package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Seance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by youssef on 12/3/17.
 */
public interface SeanceRepository extends CrudRepository<Seance,Long> {

    List<Seance> findAllByTeacherId(Long idTeacher);
    Seance findByTeacherId(Long idTeacher);

    List<Seance> findAllByGroupStudentsId(Long idStudent);

}
