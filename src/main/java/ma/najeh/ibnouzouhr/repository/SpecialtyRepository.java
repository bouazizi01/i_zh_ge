package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Specialty;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface SpecialtyRepository extends CrudRepository<Specialty,Long> {

    public boolean existsByName(String name);

    Specialty findByTeacherId(Long id);
}
