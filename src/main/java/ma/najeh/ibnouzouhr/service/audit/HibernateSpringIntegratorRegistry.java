package ma.najeh.ibnouzouhr.service.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by youssef on 12/26/17.
 */
@Component
public class HibernateSpringIntegratorRegistry {
    @Autowired(required = false)
    private List<HibernateEventListener> hibernateEventListeners;

    public List<HibernateEventListener> getHibernateEventListeners() {
        if (hibernateEventListeners == null) {
            return Collections.emptyList();
        }
        return hibernateEventListeners;
    }
}
