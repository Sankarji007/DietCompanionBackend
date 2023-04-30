import DataBaseOperation.RecipesManagement;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "SaveRecipe", value = "/SaveRecipe")
public class SaveRecipe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String recipe_id = request.getParameter("recipe_id");
        String carddata=request.getParameter("carddata");
        RecipesManagement recipes=new RecipesManagement();
        JSONObject obj= null;
        try {
            obj = recipes.Save(email,recipe_id,carddata);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        out.println(obj);
    }
}
