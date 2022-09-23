package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.service.TaskService;
import cybersoft.java18.crm.utils.CustomGson;
import cybersoft.java18.crm.utils.LocalDateAdapter;
import cybersoft.java18.crm.utils.UrlUltils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "jobs", urlPatterns = {
        UrlUltils.JOB_URL,
        UrlUltils.JOB_URL + "/*"
})
public class TaskController extends HttpServlet {
    private final TaskService service = TaskService.getInstance();
    private final ResponseData responseData = new ResponseData();

    private final Gson gson = CustomGson.GSON;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        String path = req.getRequestURI().replace(UrlUltils.CONTEXT_PATH, "");
        if (UrlUltils.TASK_URL.equals(path)) {
            List<TaskModel> jobs = service.getAllTasks();
            json = gson.toJson(jobs);
        } else {
            String taskId = path.replace(req.getServletPath() + "/", "");
            TaskModel task = service.getTaskById(taskId);
            json = gson.toJson(task);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        LocalDate startDate = LocalDate.parse(req.getParameter("start_date"), LocalDateAdapter.DATE_FORMATER);
        LocalDate endDate = LocalDate.parse(req.getParameter("end_date"), LocalDateAdapter.DATE_FORMATER);
        String userId = req.getParameter("userId");
        String jobId = req.getParameter("jobId");
        String statusId = req.getParameter("statusId");
        int result = service.saveTask(name, startDate, endDate, userId, jobId, statusId);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully add new task");
        } else {
            responseData.setStatusCode(401);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to add new task");
        }
        String json = gson.toJson(responseData);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = service.deleteTaskById(id);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully delete");
        } else {
            responseData.setStatusCode(401);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to delete");
        }
        String json = gson.toJson(responseData);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = new BufferedReader(req.getReader());
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        TaskModel taskModel = gson.fromJson(builder.toString(), TaskModel.class);
        int result = service.updateTask(taskModel);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully update");
        } else {
            responseData.setStatusCode(401);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to update");
        }
        String json = gson.toJson(responseData);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }
}
