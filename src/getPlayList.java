import DataBaseOperation.RecipesManagement;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getPlayList", value = "/getPlayList")
public class getPlayList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        RecipesManagement management=new RecipesManagement();
        JSONObject object=new JSONObject();
        object=management.GetSavedPlaylist();
        out.println(object);
    }
}
