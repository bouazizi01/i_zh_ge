package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Admin;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef on 12/3/17.
 */
public interface AdminRepository extends CrudRepository<Admin,Long> {

    boolean existsByUsername(String username);

}
