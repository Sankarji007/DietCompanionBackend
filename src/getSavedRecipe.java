import DataBaseOperation.RecipesManagement;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getSavedRecipe", value = "/getSavedRecipe")
public class getSavedRecipe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        RecipesManagement recipes=new RecipesManagement();
        JSONObject obj=recipes.getSavedRecipe(email);
        out.println(obj);

    }
}
