package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

public class TaskService {
    private static TaskService INSTANCE = new TaskService();
    private TaskRepository taskRepository = new TaskRepository();

    public static TaskService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskService();
        }
        return INSTANCE;
    }

    public List<TaskModel> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public int deleteTaskById(String id) {
        return taskRepository.deleteTask(id);
    }

    public int updateTask(TaskModel taskModel) {
        return taskRepository.updateTask(taskModel);
    }

    public int saveTask(String name, LocalDate startDate, LocalDate endDate, String userId, String jobId, String statusId) {
        return taskRepository.saveTask(name, startDate, endDate, userId, jobId, statusId);
    }


    public TaskModel getTaskById(String id) {
        return taskRepository.getTaskById(id);
    }
}
