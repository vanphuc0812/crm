package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository {
    public List<UserModel> getAllUsers() {
        return executeQuery(connection -> {
            List<UserModel> users = new ArrayList<UserModel>();
            String query = "select * from users order by users.id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setRoleId(resultSet.getInt("role_id"));
                userModel.setAvatar(resultSet.getString("avatar"));
                users.add(userModel);
            }
            return users;
        });
    }

    public UserModel getUserById(String id) {
        return (UserModel) executeSingelQuery(connection -> {
            String query = "select * from users where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));
                return userModel;
            } else return null;
        });
    }

    public int deleteUser(String id) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "delete from users where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }

    public int updateUser(UserModel userModel) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "UPDATE users SET email = ?, fullname = ?, password=?, avatar = ?, role_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getFullName());
            statement.setString(2, userModel.getPassword());
            statement.setString(3, userModel.getAvatar());
            statement.setInt(4, userModel.getRoleId());
            statement.setInt(5, userModel.getId());
            return statement.executeUpdate();
        });
    }

    public int saveUser(String email, String fullName, String password, String roleId, String avatar) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "INSERT INTO users(email, fullname, password, role_id, avatar) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, fullName);
            statement.setString(3, password);
            statement.setString(4, roleId);
            statement.setString(5, avatar);
            return statement.executeUpdate();
        });
    }

    public int saveUser(UserModel userModel) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "INSERT INTO users(email, fullname, password, role_id, avatar) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getFullName());
            statement.setString(3, userModel.getPassword());
            statement.setInt(4, userModel.getRoleId());
            statement.setString(5, userModel.getAvatar());
            return statement.executeUpdate();
        });
    }
}
