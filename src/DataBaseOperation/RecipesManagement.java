package DataBaseOperation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class RecipesManagement
{
    private final String dbName = "jdbc:postgresql://localhost:5000/CollegeProject";
    private final String dbDriver = "org.postgresql.Driver";
    private final String userName = "postgres";
    private final String password = "root";
    public JSONObject Save(String email, String recipeId, String carddata) {
        JSONObject obj = new JSONObject();
        JSONObject card=new JSONObject(carddata);

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt = conn.createStatement();
            String sql = "select * from user_saved_recipe where user_name='"+email+"'and recipe_id='"+recipeId+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifalreadyexist=rs.next();
            if(!ifalreadyexist)
            {
                sql = "INSERT INTO user_saved_recipe (user_name, recipe_id, recipe_data) VALUES (?,?,?)";
                PreparedStatement stmt1=conn.prepareStatement(sql);
                stmt1.setString(1,email);
                stmt1.setString(2,recipeId);
                stmt1.setObject(3,carddata,Types.OTHER);
                stmt1.executeUpdate();
                obj.put("success","successfully registered");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public JSONObject getSavedRecipe(String email) {
        JSONObject object=new JSONObject();
        JSONArray arr=new JSONArray();
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt = conn.createStatement();
            String sql = "select * from user_saved_recipe where user_name='" + email + "'";
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next())
            {
                String str=rs.getString(4);
                JSONObject obj=new JSONObject(str);
                arr.put(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        object.put("ResultSet",arr);
        return object;
    }

    public JSONObject DeleteRecipe(String email, String recipeId) {
        JSONObject object=new JSONObject();
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);

            String sql="delete from user_saved_recipe where user_name=? and recipe_id=?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,email);
            stmt.setString(2,recipeId);
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        object.put("success","deleted");
        return object;
    }

    public JSONObject userlogin(String email, String pass) {
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM users where email='"+email+"'and "+"password='"+pass+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("success","login successfully");
            }
            else{
                obj.put("success","login failed incorrect username or password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public JSONObject signup(String user, String email, String pass) {
        JSONObject obj = new JSONObject();
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);
            Statement stmt=conn.createStatement();
            String sql = "select * from users where username='"+user+"'or email='"+email+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifalreadyexist=rs.next();
            if(!ifalreadyexist)
            {

                sql="insert into users(username,email,password) values('"+user+"','"+email+"','"+pass+"')";
                Statement stmt1=conn.createStatement();
                stmt1.executeUpdate(sql);
                obj.put("success","successfully registered");


            }
            else{
                obj.put("success","user already exist");
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject CreatePlayList(String email, String recipeIds, String playlistname) {
        JSONObject object=new JSONObject();

        try {
                // Connect to the database
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, password);

                // Insert a new row into the user_playlists table
            PreparedStatement stmt1 = conn.prepareStatement(
                        "WITH user_data AS ( " +
                                "  SELECT id AS user_id FROM users WHERE email = ? " +
                                ") " +
                                "INSERT INTO user_playlists (user_id, playlist_name) " +
                                "VALUES (?, ?) " +
                                "RETURNING id AS playlist_id;"
                );
            stmt1.setString(1, email);
            stmt1.setString(2, email);
            stmt1.setString(3, playlistname);
            ResultSet rs1 = stmt1.executeQuery();
            rs1.next();
            int playlistId = rs1.getInt("playlist_id");

            // Insert a new row into the user_playlist_recipe table
            PreparedStatement stmt2 = conn.prepareStatement(
                        "WITH user_data AS ( " +
                                "  SELECT id AS user_id FROM users WHERE email = ? " +
                                "), " +
                                "playlist_data AS ( " +
                                "  SELECT id AS playlist_id FROM user_playlists WHERE user_id = ? AND playlist_name = ? " +
                                "), " +
                                "saved_data AS ( " +
                                "  SELECT id AS saved_id FROM user_saved_recipe WHERE recipe_ID = ? AND user_name = ? " +
                                ") " +
                                "INSERT INTO user_playlist_recipe (playlist_id, saved_id) " +
                                "SELECT playlist_id, saved_id FROM playlist_data, saved_data;"
                );
                stmt2.setString(1, email);
               stmt2.setString(2, email);
                stmt2.setString(3, playlistname);
                stmt2.setString(4, recipeIds);
                stmt2.setString(5, email);
                stmt2.executeUpdate();

                // Close the database connection
                stmt1.close();
                stmt2.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
}
