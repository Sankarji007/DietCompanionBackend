import DataBaseOperation.RecipesManagement;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;

@WebServlet(name = "GetImage", value = "/GetImage/*")
public class GetImage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathVariable = request.getPathInfo();
        pathVariable=pathVariable.substring(1);
        response.setContentType("image/jpeg");
        response.setHeader("Access-Control-Allow-Origin", "*");

        RecipesManagement recipesManagement=new RecipesManagement();
        byte[] imageData;
        try {
            imageData=recipesManagement.retriveImages(pathVariable);
            System.out.println(imageData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ServletOutputStream ostr = response.getOutputStream();
        response.setContentLength(imageData.length);
        response.setContentLength(imageData.length);
        ostr.write(imageData);
        ostr.flush();
        ostr.close();

    }

}
