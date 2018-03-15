package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Module;
import ma.najeh.ibnouzouhr.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssef on 12/15/17.
 */
@Service("ModuleService")
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public Module findOne(Long id) {
        return moduleRepository.findOne(id);
    }

    public List<Module> findBySemestre(String semestreName) {
        return moduleRepository.findAllBySemestreName(semestreName);
    }
    public List<Module> findBySemestres(String branchName, String ... semestreNames ) {
        List<Module> modules=new ArrayList<>();
        for (int i = 0; i < semestreNames.length; i++) {
            modules.addAll(moduleRepository.findAllBySemestreNameAndBranchName(semestreNames[i],branchName));
        }
        return modules;
    }




    public List<Module> findAll() {
        return (List<Module>) moduleRepository.findAll();
    }

    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    public Module update(Module module) {
        return moduleRepository.save(module);
    }


    public void delete(Long id) {
        moduleRepository.delete(id);
    }


    public Module getByCode(String code) {
        return moduleRepository.findByCode(code);
    }


    public List<Module>  findAllByBranchId(Long id) {
        return moduleRepository.findAllByBranchId(id);
    }

    public Iterable<Module> findAllBySemestreId(Long id) {
        return moduleRepository.findAllBySemestreId(id);
    }

    public Iterable<Module> findAllBySemestreIdAndBranchId(Long semestreId,Long branchId) {
        return moduleRepository.findAllBySemestreIdAndBranchId(semestreId,branchId);
    }

    public Module findByCode(String code) {
        return moduleRepository.findByCode(code);
    }

    public Module findByTeacherId(Long id) {
        return moduleRepository.findByTeacherId(id);
    }
}
