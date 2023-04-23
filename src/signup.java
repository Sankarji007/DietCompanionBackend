import DataBaseOperation.RecipesManagement;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "signup", value = "/signup")
public class signup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","POST");
        PrintWriter out=response.getWriter();
        JSONObject obj = new JSONObject();
        String user=request.getParameter("username");
        String email=request.getParameter("email");
        String pass=request.getParameter("password");
        System.out.println(user+" "+email+" "+pass);
        RecipesManagement management=new RecipesManagement();
        obj=management.signup(user,email,pass);
        out.println(obj);
    }
}
