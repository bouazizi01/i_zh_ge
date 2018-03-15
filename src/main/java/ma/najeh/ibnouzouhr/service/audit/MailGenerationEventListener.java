package ma.najeh.ibnouzouhr.service.audit;

import ma.najeh.ibnouzouhr.model.Note;
import ma.najeh.ibnouzouhr.repository.AuditLogRepository;
import ma.najeh.ibnouzouhr.service.InscriptionService;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by youssef on 12/26/17.
 */
@Component
public class MailGenerationEventListener implements HibernateEventListener, PostDeleteEventListener, PostInsertEventListener, PostUpdateEventListener {
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private InscriptionService inscriptionService;
    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Class<?> entityClass = event.getEntity().getClass();
        //System.out.println("onPostDelete "+entityClass);
        /*Map<String , Object> oldMap=ObjectUtil.makeMap(event.getPersister().getPropertyNames(),event.getDeletedState());
        ObjectMapper mapper=new ObjectMapper();
        String oldValue ="";
        try {
            oldValue=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(oldMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuditLog auditLog=new AuditLog(oldValue,null,userDetails.getUsername());
        auditLogRepository.save(auditLog);*/
    }



    @Override
    public void onPostInsert(PostInsertEvent event) {
        Class<?> entityClass = event.getEntity().getClass();

        //System.out.println("onPostInsert "+entityClass);
        /*Map<String , Object> newMap=ObjectUtil.makeMap(event.getPersister().getPropertyNames(),event.getState());
        ObjectMapper mapper=new ObjectMapper();
        String newValue ="";
        try {
            newValue=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuditLog auditLog=new AuditLog(null,newValue,userDetails.getUsername());
        //auditLogRepository.save(auditLog);*/
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        Class<?> entityClass = event.getEntity().getClass();
        /*Map<String , Object> newMap=ObjectUtil.makeMap(event.getPersister().getPropertyNames(),event.getState());
        Map<String , Object> oldMap=ObjectUtil.makeMap(event.getPersister().getPropertyNames(),event.getOldState());



        ObjectMapper mapper=new ObjectMapper();
        String oldValue ="";
        String newValue ="";
        try {
            oldValue=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(oldMap);
            newValue=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuditLog auditLog=new AuditLog(oldValue,newValue,userDetails.getUsername());
        auditLogRepository.save(auditLog);*/
        //System.out.println("onPostUpdate "+entityClass);
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
