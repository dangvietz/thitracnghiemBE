package com.quiz.app.auth;
import com.quiz.app.Message;
import com.quiz.app.email.SendEmail;
import com.quiz.app.exception.NotFoundException;
import com.quiz.app.jwt.JwtUtils;
import com.quiz.app.response.StandardJSONResponse;
import com.quiz.app.response.error.BadResponse;
import com.quiz.app.response.error.ForbiddenResponse;
import com.quiz.app.response.success.OkResponse;
import com.quiz.app.role.RoleService;
import com.quiz.app.security.UserDetailsServiceImpl;
import com.quiz.app.user.UserService;
import com.quiz.app.user.dto.ForgotPasswordResponse;
import com.quiz.app.user.dto.PostCreateUserDTO;
import com.quiz.app.user.dto.ResetPasswordDTO;
import com.quiz.app.utils.CommonUtils;
import com.quiz.app.utils.ProcessImage;
import com.quiz.entity.Role;
import com.quiz.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    public final String DEV_STATIC_DIR = "src/main/resources/static/user_images";
    public final String PROD_STATIC_DIR = "/opt/tomcat/webapps/ROOT/WEB-INF/classes/static" + "/user_images";
    public final String PROD_STATIC_PATH = "static/user_images";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${env}")
    private String environment;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("login")
    public ResponseEntity<StandardJSONResponse<User>> login(
            @RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getId().trim(),
                            loginDTO.getPassword().trim()));

            final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginDTO.getId());
            final String token = jwtUtils.generateToken(userDetails);
            User user = userService.findById(loginDTO.getId());

            if (!user.isStatus()) {
                return new ForbiddenResponse<User>(Message.ERROR_ACCOUNT_DISABLED).response();
            }

            user.setToken(token);

            return new OkResponse<>(user).response();
        } catch (BadCredentialsException | NotFoundException e) {
            return new BadResponse<User>(Message.ERROR_USER_MISMATCH).response();
        }
    }

    @GetMapping("logout")
    public ResponseEntity<StandardJSONResponse<String>> logout() {
        return new OkResponse<>(Message.LOGOUT_SUCCESSFULLY).response();
    }

    public void catchUserInputException(CommonUtils commonUtils, String id,
                                        String firstName,
                                        String lastName,
                                        String email,
                                        String password,
                                        String birthday,
                                        String sexStr,
                                        Set<Integer> roles,
                                        boolean isEdit
    ) {
        if (Objects.isNull(id) || StringUtils.isEmpty(id)) {
            commonUtils.addError("id", Message.ERROR_USERID_MISMATCH);
        } else if (id.length() > 10) {
            commonUtils.addError("id", Message.ERROR_USERID_LENGTH_LIMITED);
        }

        if (Objects.isNull(firstName) || StringUtils.isEmpty(firstName)) {
            commonUtils.addError("firstName", Message.ERROR_NAME_MISMATCH);
        }

        if (Objects.isNull(lastName) || StringUtils.isEmpty(lastName)) {
            commonUtils.addError("lastName", Message.ERROR_FNAME_MISMATCH);
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        if (Objects.isNull(email) || StringUtils.isEmpty(email)) {
            commonUtils.addError("email", Message.ERROR_EMAIL_MISMATCH);
        } else {
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                commonUtils.addError("email", Message.ERROR_INVALID_EMAIL);
            }
        }

        if (!isEdit) {
            if (Objects.isNull(password)) {
                commonUtils.addError("password", Message.ERROR_PASSWORD_IS_BLANK);
            } else if (password.length() < 8) {
                commonUtils.addError("password", Message.ERROR_PASSWORD_SMALLER_THAN8);
            }
        }

        if (Objects.isNull(birthday) || StringUtils.isEmpty(birthday)) {
            commonUtils.addError("birthday", Message.ERROR_DOB_MISMATCH);
        }

        if (Objects.isNull(sexStr) || StringUtils.isEmpty(sexStr)) {
            commonUtils.addError("sexStr", Message.ERROR_GENDER_MISMATCH);
        }

        if (roles.size() == 0) {
            commonUtils.addError("sexStr", Message.ERROR_ROLE_MISMATCH);
        }
    }

    @PostMapping("register")
    public ResponseEntity<StandardJSONResponse<String>> registerUser(@ModelAttribute PostCreateUserDTO postCreateUserDTO, @RequestParam(name = "isEdit", required = false, defaultValue = "false") boolean isEdit) throws IOException, NotFoundException {
        CommonUtils commonUtils = new CommonUtils();
        User user = null;

        String id = postCreateUserDTO.getId();
        String firstName = postCreateUserDTO.getFirstName();
        String lastName = postCreateUserDTO.getLastName();
        String email = postCreateUserDTO.getEmail();
        String password = postCreateUserDTO.getPassword();
        String birthdayStr = postCreateUserDTO.getBirthday();
        String address = postCreateUserDTO.getAddress();
        String sexStr = postCreateUserDTO.getSex();
        Set<Integer> rolesInt = postCreateUserDTO.getRoles();
        MultipartFile avatar = postCreateUserDTO.getImage();
        boolean needVerifyUser = postCreateUserDTO.isNeedVerifyUser();

        catchUserInputException(commonUtils, id, firstName, lastName, email, password, birthdayStr, sexStr, rolesInt, isEdit);

        if (commonUtils.getArrayNode().size() > 0) {
            return new BadResponse<String>(commonUtils.getArrayNode().toString()).response();
        } else {
            if (!isEdit) {
                if (userService.isIdDuplicated(id)) {
                    commonUtils.addError("id", Message.ERROR_USERID_DUPLICATED);
                }
            }

            if (userService.isBirthdayGreaterThanOrEqualTo18(LocalDate.parse(postCreateUserDTO.getBirthday()))) {
                commonUtils.addError("birthday", Message.ERROR_DOB_SMALLER_THAN18);
            }

            if (userService.isEmailDuplicated(id, email, isEdit)) {
                commonUtils.addError("email", Message.ERROR_EMAIL_DUPLICATED);
            }
            if (commonUtils.getArrayNode().size() > 0) {
                return new BadResponse<String>(commonUtils.getArrayNode().toString()).response();
            }
        }

        if(isEdit) {
            try {
                user = userService.findById(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                if (!StringUtils.isEmpty(password)) {

                    user.setPassword(password);
                    userService.encodePassword(user);

                }
                if (avatar != null) {
                    user.setAvatar(avatar.getOriginalFilename());
                }

                LocalDate birthday = LocalDate.parse(birthdayStr);
                user.setBirthday(birthday);
                user.setAddress(address);
                user.setSex(User.lookUpSex(sexStr));

                if (!StringUtils.isEmpty(password)) {
                    user.setPassword(password);
                    userService.encodePassword(user);
                }

                for (Integer role : rolesInt) {
                    // Add new role
                    user.addRole(new Role(role));
                }

                List<Role> roles = new ArrayList<>();
                for (Role role : user.getRoles()) {
                    boolean shouldDelete = true;

                    for (Integer a : rolesInt) {
                        if (role.getId().equals(a)) {
                            shouldDelete = false;
                            break;
                        }
                    }
                    // Remove none mentioned role
                    if (shouldDelete) {
                        roles.add(role);
                    }
                }

                for (Role role : roles) {
                    user.removeRole(role);
                }

                user = userService.save(user);
            } catch (NotFoundException e) {
                return new BadResponse<String>(e.getMessage()).response();
            }
        }else {
            boolean userStatus = true;
            //Nếu vai trò là sinh viên thì không cần xác thực.
            //Nếu vai trò là người dùng thì cần xác thực.
            if (needVerifyUser) {
                Role role = roleService.findById(rolesInt.iterator().next());
                if (role.getName().equals("Giảng viên")) {
                    userStatus = false;
                }
            }

            User tempUser = User.build(postCreateUserDTO, userStatus);
            userService.encodePassword(tempUser);

            for (Integer role : rolesInt) {
                tempUser.addRole(new Role(role));
            }

            user = userService.save(tempUser);
        }

        if (avatar != null) {
            String devUploadDir = String.format("%s/%s/", DEV_STATIC_DIR, user.getId());
            String prodUploadDir = String.format("%s/%s/", PROD_STATIC_DIR, user.getId());
            String staticPath = String.format("%s/%s/", PROD_STATIC_PATH, user.getId());
            ProcessImage.uploadImage(devUploadDir, prodUploadDir, staticPath, avatar, environment);
        }

        String responseMessage = "";

        if (isEdit) {
            responseMessage = Message.USER_INFO_UPDATE_SUCCESSFULLY;
        } else if (!needVerifyUser) {
            responseMessage = Message.USER_ADD_SUCCESSFULLY;
        } else {
            responseMessage = Message.USER_REGISTER_SUCCESSFULLY;
        }

        return new OkResponse<>(responseMessage).response();

    }

    @PostMapping("forgot-password")
    public ResponseEntity<StandardJSONResponse<ForgotPasswordResponse>> forgotPassword(
            @RequestBody Map<String, String> payLoad) throws MessagingException {
        String email = payLoad.get("email");
        try {
            User user = userService.findByEmail(email);
            Random rand = new Random();
            int resetPasswordCode = rand.nextInt(999999) + 1;

            user.setResetPasswordCode(resetPasswordCode);
            user.setResetPasswordExpirationTime(LocalDateTime.now().plusMinutes(30));
            userService.save(user);

            // Include email as a query parameter in the reset password link
            String resetPasswordLink = "http://localhost:3000/auth/resetpassword?email=" + email + "&code=" + resetPasswordCode;

            String msg = "Hi " + user.getFullName() + "<div>Need to reset your password?</div>" + "</div>"
                    + "<div>Click on the link below and enter the secret code above.</div>"
                    + "<a href='" + resetPasswordLink + "'>Reset your password</a>"
                    + "<div>If you did not forget your password, you can ignore this email.</div>"
                    + resetPasswordCode;

            SendEmail.send(user.getEmail(), "Reset your password - QuizApp", msg);
            String message = Message.EMAIL_SEND_SUCCESSFULLY + user.getEmail();
            ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse(resetPasswordCode, message,
                    email);

            return new OkResponse<>(forgotPasswordResponse).response();
        } catch (NotFoundException e) {
            return new BadResponse<ForgotPasswordResponse>(e.getMessage()).response();
        }
    }


    @RequestMapping("resetpassword")
    public ResponseEntity<StandardJSONResponse<String>> resetPassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "code") int resetPasswordCode,
            @RequestBody ResetPasswordDTO resetPassword) {
        if (resetPassword.getEmail().isEmpty()) {
            return new BadResponse<String>(Message.ERROR_EMAIL_ISBLANK)
                    .response();
        }
        String newPassword = resetPassword.getNewPassword();
        String confirmNewPassword = resetPassword.getConfirmNewPassword();
        LocalDateTime now = LocalDateTime.now();

        try {
            User user = userService.findByEmail(email);

            // Check if the user's reset password code is null
            if (user.getResetPasswordCode() == null) {
                return new BadResponse<String>(Message.ERROR_TOKENCODE_EXPIRED)
                        .response();
            }

            if (resetPasswordCode != user.getResetPasswordCode())
                return new BadResponse<String>(Message.ERROR_TOKEN_INVALID).response();

            boolean isAfter = now.isAfter(user.getResetPasswordExpirationTime());
            if (isAfter)
                return new BadResponse<String>(Message.ERROR_TIME_EXPIRED).response();

            if (newPassword.length() < 8) {
                return new BadResponse<String>(Message.ERROR_PASSWORD_SMALLER_THAN8).response();
            }
            if (newPassword.length() > 32) {
                return new BadResponse<String>(Message.ERROR_PASSWORD_MAX32).response();
            }

            if (!newPassword.equals(confirmNewPassword))
                return new BadResponse<String>(Message.ERROR_PASSWORD_NOTMATCH).response();

            user.setPassword(userService.getEncodedPassword(newPassword));
            user.setResetPasswordExpirationTime(null);
            user.setResetPasswordCode(null);
            userService.save(user);

            return new OkResponse<>(Message.PASSWORD_RESET_SUCCESSFULLY).response();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new BadResponse<String>(e.getMessage()).response();
        }
    }



}
