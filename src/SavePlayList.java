import DataBaseOperation.RecipesManagement;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SavePlayList", value = "/SavePlayList")
public class SavePlayList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String myArray = request.getParameter("recipeId");
        String playlistname=request.getParameter("playlistname");

        String recipeIds[]=myArray.split(",");

        System.out.println(email);
        RecipesManagement management=new RecipesManagement();
        JSONObject object=new JSONObject();
        if(recipeIds.length>1)
        {
            for (String x:recipeIds)
              management.CreatePlayList(email,x,playlistname);
        }
        else
        {
            object.put("success","more than one recipe is needed to create playlist");
        }
        object.put("success","Play List Created");
        out.println(object);
    }
}
