package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Planing;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef on 12/3/17.
 */
public interface PlaningRepository extends CrudRepository<Planing,Long> {

    Iterable<Planing> findAllBySeanceTeacherId(Long id);
}
