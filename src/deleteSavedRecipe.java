import DataBaseOperation.RecipesManagement;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "deleteSavedRecipe", value = "/deleteSavedRecipe")
public class deleteSavedRecipe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String recipe_id = request.getParameter("recipe_id");
        RecipesManagement recipesManagement=new RecipesManagement();

        JSONObject object=new JSONObject();
        object=recipesManagement.DeleteRecipe(email,recipe_id);
        out.println(object);
    }
}
