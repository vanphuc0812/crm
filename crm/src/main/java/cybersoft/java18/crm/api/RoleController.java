package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.service.RoleService;
import cybersoft.java18.crm.utils.UrlUltils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "role", urlPatterns = UrlUltils.ROLE_URL)
public class RoleController extends HttpServlet {
    private Gson gson = new Gson();
    private RoleService roleService = RoleService.getInstance();
    private ResponseData responseData = new ResponseData();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleModel> roles = roleService.getAllRoles();
        String json = gson.toJson(roles);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
        String description = req.getParameter("description");
        int result = roleService.saveRole(role, description);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully add new role");
        } else {
            responseData.setStatusCode(200);
            responseData.setSuccess(false);
            responseData.setMessage("Failed to add new role");
        }
        String json = gson.toJson(responseData);

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = roleService.deleteRoleById(id);
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setSuccess(true);
            responseData.setMessage("Sucessfully delete");
        } else {
            responseData.setStatusCode(200);
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
        RoleModel roleModel = gson.fromJson(builder.toString(), RoleModel.class);
        System.out.println(roleModel.toString());
        roleService.updateRole(roleModel);
    }


}
