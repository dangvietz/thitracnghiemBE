package com.quiz.app.creditClass;

import com.quiz.entity.CreditClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CreditClassRepository extends CrudRepository<CreditClass, Integer> {
    @Query(value = "SELECT * FROM loptinchi as ltc WHERE ltc.nienkhoa = :schoolYear AND ltc.hocky" +
            " = :semester AND ltc.mamh = :subjectId AND ltc.nhom = :group", nativeQuery = true)
    CreditClass findByUniqueKey(String schoolYear, double semester, String subjectId,
                                int group);

    @Query(value = "SELECT ltc.* FROM loptinchi ltc join dangky dk on dk.maltc = ltc.maltc WHERE huylop = false group by ltc.maltc", nativeQuery = true)
    List<CreditClass> findAllActiveCreditClassHaveRegister();

    @Query(value = "SELECT * FROM loptinchi ltc WHERE huylop = false", nativeQuery = true)
    List<CreditClass> findAllActiveCreditClass();

    @Query(value = "SELECT ltc.* FROM loptinchi ltc join dangky dk on dk.maltc = ltc.maltc WHERE " +
            "huylop = false and dk.masv = :studentId group by ltc.maltc", nativeQuery = true)
    List<CreditClass> findAllActiveCreditClassAndStudent(String studentId);

    @Query("SELECT count(*) FROM CreditClass")
    Integer countTotalCreditClasses();

    @Query("SELECT count(*) FROM CreditClass c where c.status = false")
    Integer countTotalCreditClassesOpened();

    @Query("SELECT count(*) FROM CreditClass c where c.status = true")
    Integer countTotalCreditClassesClosed();
}
