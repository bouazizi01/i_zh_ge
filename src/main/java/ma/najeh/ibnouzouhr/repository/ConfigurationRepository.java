package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Configuration;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef on 12/3/17.
 */
public interface ConfigurationRepository extends CrudRepository<Configuration,String> {
     Configuration findByName(String name);
}
