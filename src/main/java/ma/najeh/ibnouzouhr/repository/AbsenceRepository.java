package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Absence;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef on 12/3/17.
 */
public interface AbsenceRepository extends CrudRepository<Absence,Long> {


    Absence findByPlaningId(Long id);
}
