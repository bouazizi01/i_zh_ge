package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface NoteRepository extends CrudRepository<Note,Long> , JpaRepository<Note,Long> {

    List<Note> findAllByStudentId(long id);

    Note findByModuleIdAndStudentId(Long moduleId, Long studentId);
}
