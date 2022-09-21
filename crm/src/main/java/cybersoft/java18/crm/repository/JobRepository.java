package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.JobModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobRepository extends AbstractRepository {
    public List<JobModel> getAllJobs() {
        return executeQuery(connection -> {
            List<JobModel> jobs = new ArrayList<JobModel>();
            String query = "select * from jobs";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getDate("start_date").toLocalDate());
                jobModel.setEndDate(resultSet.getDate("end_date").toLocalDate());
                jobs.add(jobModel);
            }
            return jobs;
        });
    }

    public JobModel getJobById(String id) {
        return (JobModel) executeSingelQuery(connection -> {
            String query = "select * from jobs where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getDate("start_date").toLocalDate());
                jobModel.setEndDate(resultSet.getDate("end_date").toLocalDate());
                return jobModel;
            } else return null;
        });
    }

    public int deleteJob(String id) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "delete from jobs where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }

    public int updateJob(JobModel jobModel) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "UPDATE jobs SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, jobModel.getName());
            statement.setDate(2, Date.valueOf(jobModel.getStartDate()));
            statement.setDate(3, Date.valueOf(jobModel.getEndDate()));
            statement.setInt(4, jobModel.getId());
            return statement.executeUpdate();
        });
    }

    public int saveJob(String name, LocalDate startDate, LocalDate endDate) {
        return (int) executeSaveAndUpdate(connection -> {
            String query = "INSERT INTO jobs(name, start_date, end_date) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            return statement.executeUpdate();
        });
    }


}
