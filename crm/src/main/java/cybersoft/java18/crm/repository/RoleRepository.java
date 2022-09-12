package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.RoleModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends AbstractRepository {
    public List<RoleModel> getAllRoles() {
        return executeQuery(connection -> {
            List<RoleModel> roles = new ArrayList<RoleModel>();
            String query = "select * from roles";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));
                roles.add(roleModel);
            }
            return roles;
        });
    }

    public int deleteRole(String id) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "delete from roles where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }

    public int updateRole(RoleModel roleModel) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());
            statement.setInt(3, roleModel.getId());
            return statement.executeUpdate();
        });
    }

    public int saveRole(String role, String description) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "INSERT INTO roles(name, description) VALUES(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, role);
            statement.setString(2, description);
            return statement.executeUpdate();
        });
    }
}
