package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Reclamation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * Created by BOUAZIZI on 12/3/17.
 */

public interface ReclamationRepository extends CrudRepository<Reclamation,Long> {
    Iterable<Reclamation> findAllByStudentId(Long id);
    @Query(value = "select * FROM reclamation r INNER JOIN note n on r.note_id = n.id INNER JOIN module m on n.module_id = m.id INNER JOIN branch b on m.branch_id = b.id INNER JOIN specialty s on b.specialty_id = s.id where s.id=:id",nativeQuery = true)
    Iterable<Reclamation> allReclamationsbySpeciality(@Param("id") Long id);
    @Query(value = "select * FROM reclamation r INNER JOIN note n on r.note_id = n.id INNER JOIN module m on n.module_id = m.id  where m.id=:id AND r.state=:state",nativeQuery = true)
    Iterable<Reclamation> allReclamationsbyModuleAndState(@Param("id") Long id,@Param("state") String state);
}
