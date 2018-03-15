package ma.najeh.ibnouzouhr.service.audit;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by youssef on 12/26/17.
 */
@Component
public class HibernateSpringIntegrator {
    private static final Logger log = LoggerFactory.getLogger(HibernateSpringIntegrator.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private HibernateSpringIntegratorRegistry hibernateSpringIntegratorRegistry;


    @PostConstruct
    public void registerListeners() {
        log.debug("Registering Spring managed HibernateEventListeners");
        HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) this.entityManagerFactory;
        EventListenerRegistry listenerRegistry =  hibernateEntityManagerFactory
                .getSessionFactory().getServiceRegistry().getService(
                EventListenerRegistry.class);
        List<HibernateEventListener> eventListeners = hibernateSpringIntegratorRegistry
                .getHibernateEventListeners();
        for (HibernateEventListener hel : eventListeners) {
            log.debug("Registering: {}", hel.getClass());
            if (PreInsertEventListener.class.isAssignableFrom(hel.getClass())) {
                listenerRegistry.appendListeners(EventType.PRE_INSERT,
                        (PreInsertEventListener) hel);
            }
            if (PreUpdateEventListener.class.isAssignableFrom(hel.getClass())) {
                listenerRegistry.appendListeners(EventType.PRE_UPDATE,
                        (PreUpdateEventListener) hel);
            }
            if (PreDeleteEventListener.class.isAssignableFrom(hel.getClass())) {
                listenerRegistry.appendListeners(EventType.PRE_DELETE,
                        (PreDeleteEventListener) hel);
            }
            if (PostInsertEventListener.class.isAssignableFrom(hel.getClass())) {
                listenerRegistry.appendListeners(EventType.POST_INSERT,
                        (PostInsertEventListener) hel);
            }
            if (PostUpdateEventListener.class.isAssignableFrom(hel.getClass())) {
                listenerRegistry.appendListeners(EventType.POST_UPDATE,
                        (PostUpdateEventListener) hel);
            }
            if (PostDeleteEventListener.class.isAssignableFrom(hel.getClass())) {
                listenerRegistry.appendListeners(EventType.POST_DELETE,
                        (PostDeleteEventListener) hel);
            }
            // Currently we do not need other types of eventListeners. Else this method needs to be extended.
        }
    }

}
