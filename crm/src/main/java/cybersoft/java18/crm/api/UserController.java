package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.UserService;
import cybersoft.java18.crm.utils.UrlUltils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@WebServlet(name = "users", urlPatterns = {
        UrlUltils.USER_URL,
        UrlUltils.USER_URL + "/*"
})
@MultipartConfig
public class UserController extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserService service = UserService.getInstance();
    private final ResponseData responseData = new ResponseData();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        String path = req.getRequestURI().replace(req.getContextPath(), "");
        if (UrlUltils.USER_URL.equals(path)) {
            List<UserModel> users = service.getAllUsers();
            json = gson.toJson(users);
        } else {
            String userId = path.replace(req.getServletPath() + "/", "");
            UserModel user = service.getUserById(userId);
            json = gson.toJson(user);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String roleId = req.getParameter("role");
        Part filePart = req.getPart("avatar");
        String path = req.getServletContext().getRealPath("/avatars") + "\\";
        String fileName = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + ".png";
        filePart.write(path + fileName);

        int result = service.saveUser(name, email, password, roleId, "/avatars" + fileName);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully add new user");
        } else {
            responseData.setStatusCode(400);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to add new user");
        }
        String json = gson.toJson(responseData);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = service.deleteUserById(id);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully delete");
        } else {
            responseData.setStatusCode(400);
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
        UserModel userModel = gson.fromJson(builder.toString(), UserModel.class);
        int result = service.updateUser(userModel);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully update");
        } else {
            responseData.setStatusCode(400);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to update");
        }
        String json = gson.toJson(responseData);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }
}
