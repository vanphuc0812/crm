package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository {
    public List<UserModel> getAllUsers() {
        return executeQuery(connection -> {
            List<UserModel> users = new ArrayList<UserModel>();
            String query = "select * from users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                users.add(userModel);
            }
            return users;
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
            String query = "UPDATE users SET email = ?, fullname = ?, avatar = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getFullName());
            statement.setString(3, userModel.getAvatar());
            return statement.executeUpdate();
        });
    }

    public int saveUser(String email, String fullName, String avatar) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "INSERT INTO users(email, fullname, avatar) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, fullName);
            statement.setString(3, avatar);
            return statement.executeUpdate();
        });
    }
}
