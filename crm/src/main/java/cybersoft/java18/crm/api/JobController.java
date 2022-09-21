package cybersoft.java18.crm.api;

import com.google.gson.*;
import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.service.JobService;
import cybersoft.java18.crm.utils.DateTimeFormat;
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
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "jobs", urlPatterns = {
        UrlUltils.JOB_URL,
        UrlUltils.JOB_URL + "/*"
})
public class JobController extends HttpServlet {
    private final JobService jobService = JobService.getInstance();
    private final ResponseData responseData = new ResponseData();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormat.DATE_FORMATER);
                }
            })
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        String path = req.getRequestURI().replace(UrlUltils.CONTEXT_PATH, "");
        if (UrlUltils.JOB_URL.equals(path)) {
            List<JobModel> jobs = jobService.getAllJobs();
            json = gson.toJson(jobs);
        } else {
            String jobId = path.replace(req.getServletPath() + "/", "");
            JobModel job = jobService.getJobById(jobId);
            json = gson.toJson(job);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        System.out.println(name);
        System.out.println(req.getParameter("start_date"));
        System.out.println(req.getParameter("end_date"));
        LocalDate startDate = LocalDate.parse(req.getParameter("start_date"), DateTimeFormat.DATE_FORMATER);
        LocalDate endDate = LocalDate.parse(req.getParameter("end_date"), DateTimeFormat.DATE_FORMATER);
        System.out.println(startDate);
        System.out.println(endDate);
        int result = jobService.saveJob(name, startDate, endDate);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully add new job");
        } else {
            responseData.setStatusCode(401);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to add new job");
        }
        String json = gson.toJson(responseData);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = jobService.deleteJobById(id);
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
        System.out.println(builder);
        JobModel jobModel = gson.fromJson(builder.toString(), JobModel.class);
        int result = jobService.updateJob(jobModel);
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
