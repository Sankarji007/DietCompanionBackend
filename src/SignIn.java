import DataBaseOperation.RecipesManagement;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignIn", value = "/SignIn")
public class SignIn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out=response.getWriter();
        String email=request.getParameter("email");
        String pass=request.getParameter("password");
        JSONObject obj = new JSONObject();
        RecipesManagement management=new RecipesManagement();
        obj=management.userlogin(email,pass);
        out.println(obj);
    }
}
