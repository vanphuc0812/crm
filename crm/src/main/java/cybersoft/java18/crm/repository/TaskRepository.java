package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.TaskModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends AbstractRepository {
    public List<TaskModel> getAllTasks() {
        return executeQuery(connection -> {
            List<TaskModel> tasks = new ArrayList<>();
            String query = "select * from tasks";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setStartDate(resultSet.getDate("start_date").toLocalDate());
                taskModel.setEndDate(resultSet.getDate("end_date").toLocalDate());
                taskModel.setUserId(resultSet.getInt("user_id"));
                taskModel.setJobId(resultSet.getInt("job_id"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
                tasks.add(taskModel);
            }
            return tasks;
        });
    }

    public TaskModel getTaskById(String id) {
        return (TaskModel) executeSingelQuery(connection -> {
            String query = "select * from tasks where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setStartDate(resultSet.getDate("start_date").toLocalDate());
                taskModel.setEndDate(resultSet.getDate("end_date").toLocalDate());
                taskModel.setUserId(resultSet.getInt("user_id"));
                taskModel.setJobId(resultSet.getInt("job_id"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
                return taskModel;
            } else return null;
        });
    }

    public int deleteTask(String id) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "delete from tasks where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }

    public int updateTask(TaskModel taskModel) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "UPDATE tasks SET name = ?, start_date = ?, end_date = ?, user_id =?, job_id=?, status_id=? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskModel.getName());
            statement.setDate(2, Date.valueOf(taskModel.getStartDate()));
            statement.setDate(3, Date.valueOf(taskModel.getEndDate()));
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getJobId());
            statement.setInt(6, taskModel.getStatusId());
            statement.setInt(7, taskModel.getId());
            return statement.executeUpdate();
        });
    }

    public int saveTask(String name, LocalDate startDate, LocalDate endDate, String userId, String jobId, String statusId) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "INSERT INTO tasks(name, start_date, end_date, user_id, job_id,status_id) VALUES(?, ?, ?, ?,?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            statement.setString(4, userId);
            statement.setString(5, jobId);
            statement.setString(6, statusId);
            return statement.executeUpdate();
        });
    }

}
