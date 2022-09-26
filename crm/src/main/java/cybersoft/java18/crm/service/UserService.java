package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.repository.UserRepository;

import java.util.List;

public class UserService {
    private static UserService INSTANCE = new UserService();
    private final UserRepository userRepository = new UserRepository();

    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserModel getUserById(String id) {
        return userRepository.getUserById(id);
    }
    public int deleteUserById(String id) {
        return userRepository.deleteUser(id);
    }

    public int updateUser(UserModel roleModel) {
        return userRepository.updateUser(roleModel);
    }

    public int saveUser(String email, String fullName, String password, String roleId, String avatar) {
        return userRepository.saveUser(email, fullName, password, roleId, avatar);
    }

    public int saveUser(UserModel roleModel) {
        return userRepository.saveUser(roleModel);
    }
}
