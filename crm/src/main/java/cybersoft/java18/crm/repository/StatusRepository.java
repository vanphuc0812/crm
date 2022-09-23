package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.StatusModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository extends AbstractRepository<StatusModel> {
    public List<StatusModel> getAllStatus() {
        return executeQuery(connection -> {
            List<StatusModel> status = new ArrayList<>();
            String query = "select * from status";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));
                status.add(statusModel);
            }
            return status;
        });
    }

    public StatusModel getStatusById(int id) {
        return executeSingelQuery(connection -> {
            String query = "select * from status where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));
                return statusModel;
            } else return null;
        });
    }
}
