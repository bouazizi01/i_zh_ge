package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,Long> {
}
