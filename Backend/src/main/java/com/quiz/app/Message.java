package com.quiz.app;

public class Message {
    public static final String ERROR_USER_MISMATCH=" Tài khoản hoặc mật khẩu không đúng!";
    public static final String ERROR_EMAIL_MISMATCH=" Email không được để trống";
    public static final String ERROR_INVALID_EMAIL=" Địa chỉ email không hợp lệ, vui lòng nhập đúng định dạng email@example.com";
    public static final String ERROR_GENDER_MISMATCH="Giới tính không được để trống";
    public static final String ERROR_ACCOUNT_DISABLED="Tài khoản đang bị vô hiệu hóa";
    public static final String ERROR_ROLE_MISMATCH="Vai trò không được để trống";
    public static final String ERROR_USERID_MISMATCH="Mã người dùng không được để trống";
    public static final String ERROR_USERID_LENGTH_LIMITED="Mã người dùng tối đa 10 ký tự";
    public static final String ERROR_DOB_MISMATCH="Ngày sinh không được để trống";
    public static final String ERROR_NAME_MISMATCH="Tên không được để trống";
    public static final String ERROR_FNAME_MISMATCH="Họ không được để trống";
    public static final String ERROR_PASSWORD_IS_BLANK="Ngày sinh không được để trống";
    public static final String ERROR_PASSWORD_SMALLER_THAN8="Mật khẩu ít nhất 8 ký tự";
    public static final String ERROR_PASSWORD_MAX32="Mật khẩu tối đa 32 ký tự";
    public static final String ERROR_USERID_DUPLICATED="Mã người dùng đã tồn tại";
    public static final String ERROR_DOB_SMALLER_THAN18="Tuổi phải lớn hơn 18";
    public static final String ERROR_EMAIL_DUPLICATED="Địa chỉ email đã tồn tại";
    public static final String USER_INFO_UPDATE_SUCCESSFULLY="Cập nhật thông tin người dùng thành công";
    public static final String USER_ADD_SUCCESSFULLY="Thêm người dùng thành công";
    public static final String USER_REGISTER_SUCCESSFULLY="Đăng ký thành công";
    public static final String ERROR_EMAIL_ISBLANK="Cần nhập Email để khôi phục mật khẩu";
    public static final String ERROR_TOKENCODE_EXPIRED="Link khôi phục hết hạn. Vui lòng lấy link khác";
    public static final String ERROR_TOKEN_INVALID="Sai code khôi phục";
    public static final String ERROR_TIME_EXPIRED="Hết thời hạn khôi phục mật khẩu";
    public static final String ERROR_PASSWORD_NOTMATCH="Mật khẩu mới không trùng mật khẩu cũ, vui lòng nhập lại";
    public static final String PASSWORD_RESET_SUCCESSFULLY="Đổi mật khẩu thành công";
    public static final String LOGOUT_SUCCESSFULLY="Đăng xuất thành công";
    public static final String EMAIL_SEND_SUCCESSFULLY="Link khôi phục mật khẩu đã được gửi qua email " + " ";
    public static final String ERROR_SUBJECTID_MISMATCH="Mã môn học không được để trống";
    public static final String ERROR_SUBJECTID_DUPLICATED="Mã môn học đã tồn tại";
    public static final String ERROR_SUBJECTNAME_MISMATCH="Tên môn học không được để trống";
    public static final String ERROR_numberOfTheoreticalPeriods_MISMATCH="Số tiết lý thuyết không được để trống";
    public static final String ERROR_numberOfPracticePeriods_MISMATCH="Số tiết thực hành không được để trống";
    public static final String ERROR_numberOfPeriods_MISMATCH="Số tiết lý thuyết + thực " +
            "hành phải là bội số của 3 ";
    public static final String ERROR_CHAPTER_CONSTRAINT="Không thể xóa chương vì ràng buộc dữ liệu";
    public static final String SUBJECT_ADD_SUCCESSFULLY="Thêm môn học thành công";
    public static final String ERROR_QUESTION_CONTENT_MISMATCH="Nội dung câu hỏi không được để trống";
    public static final String ERROR_QUESTIONTYPE_CONTENT_MISMATCH="Loại câu hỏi không được để trống";
    public static final String ERROR_QUESTION_LEVEL_MISMATCH="Mức độ không được để trống";
    public static final String ERROR_ANSWER_NULL="Không được để trống sự lựa chọn";
    public static final String ERROR_CHAPTER_NULL="Chương không được để trống";
    public static final String QUESTION_EDIT_SUCCESSFULLY="Thêm hoặc sửa câu hỏi thành công";
    public static final String ERROR_QUESTION_DUPLICATED="Tất cả câu hỏi đã được thêm từ trước";
    public static final String QUESTION_ADDED="%d/%d câu hỏi đã được thêm vào thành công";
    public static final String ERROR_EXCEL_READ_ERROR="Không thể đọc được nội dung từ file Excel";
    public static final String ERROR_EXAMS_ID_NULL="Mã ca thi không được để " +
            "trống";
    public static final String ERROR_CREDITCLASS_ID_NULL="Mã LTC không được để " +
            "trống";
    public static final String ERROR_EXAMS_DATE_NULL="Số tiết thực hành không được để " +
            "trống";
    public static final String ERROR_NOTICE_PERIOD_NULL="Tiết báo danh không được để " +
            "trống";
    public static final String ERROR_STUDENT_NUM_NULL="Số SV không được để trống ";
    public static final String ERROR_EXAMS_TIME_NULL="Thời gian thi không được để " +
            "trống";
    public static final String ERROR_EXAMS_TYPE_NULL="Loại kỳ thi không được để " +
            "trống";
    public static final String ERROR_TESTS_NULL="Loại kỳ thi không được để " +
            "trống";
    public static final String CATHI_ADD_SUCCESSFULLY="Thêm ca thi thành công";
    public static final String CATHI_DELETE_PERMISSION="Người dùng không có quyền xoá ca thi";
    public static final String CATHI_DELETE_FALSE="Không thể xóa ca thi";
    public static final String ERROR_SCHOOLYEAR_NULL="Niên khóa không được để trống";
    public static final String ERROR_SEMESTER_NULL="Học kỳ không được để trống";
    public static final String ERROR_SUBJECT_ID_NULL="Môn học không được để trống";
    public static final String ERROR_SUBJECT_GROUP_NULL="Nhóm không được để trống";
    public static final String ERROR_TEACHER_NULL="Giảng viên không được để trống";
    public static final String ERROR_UNIQUE_KEY="Niên khóa + học kỳ + môn học + nhóm phải là " +
            "duy nhất";
    public static final String CREDIT_CLASS_ADD_SUCCESSFULLY="Thêm lớp tín chỉ thành công";
    public static final String ANSWER_DELETE_SUCCESSFULLY="Xóa lựa chọn thành công";
    public static final String ANSWER_DELETE_CONSTRAINT="Không thể xóa môn học vì ràng buộc dữ liệu";
    public static final String SUBJECT_ID_NOTFOUND="Không tìm thấy môn học với mã ";
    public static final String DELETE_CATHI_SUCCESSFULLY="Xóa ca thi thành công";
    public static final String DELETE_CATHI_CONSTRAINT="Không thể xóa ca thi vì ràng buộc dữ liệu";
    public static final String ENABLE_CATHI_SUCCESSFULLY="Kích họat ca thi thành công";
    public static final String DISABLE_CATHI_SUCCESSFULLY="Hủy ca thi thành công";
    public static final String DELETE_CREDITCLASS_SUCCESSFULLY="Xóa lớp tín chỉ thành công";
    public static final String DELETE_CREDITCLASS_CONSTRAINT="Không thể xóa lớp tín chỉ vì ràng buộc dữ liệu";
    public static final String CLASS_ID_NOTFOUND="Không tìm thấy lớp học với mã ";
    public static final String ERROR_CREDITCLASS_ID_NOTFOUND="Không tìm thấy lớp tín chỉ này";
    public static final String ENABLE_CREDITCLASS_SUCCESSFULLY="Mở lớp tín chỉ thành công";
    public static final String DISABLE_CREDITCLASS_SUCCESSFULLY="Hủy lớp tín chỉ thành công";
    public static final String DELETE_CHAPTER_SUCCESSFULLY="Xóa chương thành công";
    public static final String DELETE_CHAPTER_CONSTRAINT="Không thể xóa chương vì ràng buộc dữ liệu";
    public static final String SUBJECT_NAMENOTFOUND="Không tìm thấy môn học với tên ";
    public static final String ADMIN_USER_ADD_SUCCESSFULLY="%d/%d người dùng được thêm thành công";
    public static final String ENABLE_USER_SUCCESSFULLY="Kích hoạt người dùng thành công";
    public static final String DISABLE_USER_SUCCESSFULLY="H" +
            "ủy " +
            "kích hoạt người dùng thành công";

    public static final String ERROR_TESTID_NULL="Mã đề thi không được để trống";
    public static final String ERROR_TESTNAME_NULL="Tên đề thi không được để trống";
    public static final String ERROR_QUESTIONLIST_NULL="Danh sách câu hỏi không được để trống";
    public static final String ERROR_TESTNAME_DUPLICATED="Tên đề thi đã tồn tại";
    public static final String DETHI_ADD_SUCCESSFULLY="Thêm đề thi thành công";
}
