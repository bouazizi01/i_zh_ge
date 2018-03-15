package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface StudentRepository extends CrudRepository<Student,Long> {
    Student findByCodeAPOGEE(String codeAPOGEE);
    List<Student> findAllByGroupsId(Long id);
    Student findByUsername(String username);
    @Query("from Student as s WHERE s.password LIKE concat('%',?1)")
    List<Student> findByPasswordLike(String invalid);
    long count();

    boolean existsByCne(String cne);

    boolean existsByCin(String cin);

    Iterable<Student> findAllByBranchId(Long branchId);

    List<Student> findAllByCodeAPOGEEOrCneOrCinOrFirstNameOrLastName(String code,String cne,String cin,String first,String last);
    //@Query(value = "select s.* FROM students s INNER JOIN inscription i on i.student_id = s.id INNER JOIN module m on i.module_id = m.id where m.id=:id",nativeQuery = true)
    List<Student> findAllByInscriptions_ModuleId(@Param("id") Long id);
}
