package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.service.StatusService;
import cybersoft.java18.crm.utils.CustomGson;
import cybersoft.java18.crm.utils.UrlUltils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "status", urlPatterns = {
        UrlUltils.STATUS_URL,
        UrlUltils.STATUS_URL + "/*"
})
public class StatusController extends HttpServlet {
    private final Gson gson = CustomGson.GSON;
    private final StatusService service = StatusService.getInstance();
    private final ResponseData responseData = new ResponseData();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        String path = req.getRequestURI().replace(req.getContextPath(), "");
        if (UrlUltils.STATUS_URL.equals(path)) {
            List<StatusModel> status = service.getAllStatus();
            json = gson.toJson(status);
        } else {
            int statusId;
            try {
                statusId = Integer.parseInt(path.replace(req.getServletPath() + "/", ""));
                StatusModel status = service.getStatusById(statusId);
                json = gson.toJson(status);
            } catch (NumberFormatException e) {
                responseData.setStatusCode(400);
                responseData.setSuccess(false);
                responseData.setMessage("Url request status id is incorrect");
                json = gson.toJson(responseData);
            }
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

}
