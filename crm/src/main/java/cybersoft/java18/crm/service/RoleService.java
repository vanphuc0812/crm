package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.repository.RoleRepository;

import java.util.List;

public class RoleService {
    private static RoleService INSTANCE = new RoleService();
    private RoleRepository roleRepository = new RoleRepository();

    public static RoleService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleService();
        }
        return INSTANCE;
    }

    public List<RoleModel> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    public int deleteRoleById(String id) {
        return roleRepository.deleteRole(id);
    }

    public int updateRole(RoleModel roleModel) {
        return roleRepository.updateRole(roleModel);
    }

    public int saveRole(String role, String description) {
        return roleRepository.saveRole(role, description);
    }

    public RoleModel getRoleById(int id) {
        return roleRepository.getRoleById(id);
    }
}
