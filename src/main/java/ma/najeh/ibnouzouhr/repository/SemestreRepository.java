package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Semestre;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface SemestreRepository extends CrudRepository<Semestre,Long> {


    boolean existsByName(String name);
}
