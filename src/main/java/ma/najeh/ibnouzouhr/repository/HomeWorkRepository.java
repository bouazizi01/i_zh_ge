package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.HomeWork;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Hamza on 04/01/18.
 */
public interface HomeWorkRepository extends CrudRepository<HomeWork,Long> {

    List<HomeWork> findAllBySeanceId(Long idSeance);

    Iterable<HomeWork> findAllBySeanceTeacherId(Long id);

    Iterable<HomeWork> findAllBySeanceGroupStudentsId(Long id);

}
