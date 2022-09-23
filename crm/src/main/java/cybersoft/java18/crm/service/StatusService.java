package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.repository.StatusRepository;

import java.util.List;

public class StatusService {
    private static StatusService INSTANCE = new StatusService();
    private final StatusRepository repository = new StatusRepository();

    public static StatusService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatusService();
        }
        return INSTANCE;
    }

    public List<StatusModel> getAllStatus() {
        return repository.getAllStatus();
    }


    public StatusModel getStatusById(int id) {
        return repository.getStatusById(id);
    }
}
