package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.DemandDocumentStudent;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by BOUAZIZI on 12/16/17.
 */

public interface DemandDocumentStudentRepository extends CrudRepository<DemandDocumentStudent,Long> {
    Iterable<DemandDocumentStudent> findAllByStudentId(Long id);
}
