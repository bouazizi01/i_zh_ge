package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher findByCin(String cin);
    boolean existsByCin(String cin);
    //@Query(value = "select p.* FROM planing p INNER JOIN seance s on p.seance_id = s.id INNER JOIN teachers t on s.teacher_id = t.id where t.id=:id",nativeQuery = true)
    @Query(value = "select count(*) FROM planing p INNER JOIN seance s on p.seance_id = s.id INNER JOIN teachers t on s.teacher_id = t.id where EXISTS (select planing.id from planing where (day_id=:dayId) AND ( (start_h > :startH AND start_h < :endH ) OR (end_h > :startH AND end_h < :endH )))  AND t.id=:id",nativeQuery = true)
    long planingsOfATeacher(@Param("id") Long id,@Param("startH") Integer startH,@Param("endH") Integer endH,@Param("dayId") Long dayId);
}
