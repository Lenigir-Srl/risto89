import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

import java.sql.*;

// This servlet tests the connection with the database
//
// Outputs
// - OK if the connection is successful
// - ERR otherwise, with the error message
public class TestDB extends HttpServlet {

  public void doGet (HttpServletRequest req,
                     HttpServletResponse res)
    throws ServletException, IOException
  {
    
    try {

        // Loading the driver
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        try {
            // Connecting
            String url = "jdbc:derby://localhost:1527/DemoDB";
             Connection con = DriverManager.getConnection(url);

            // Query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            // Output
            PrintWriter out = res.getWriter();

            while (rs.next()) {

                String s = rs.getString("NAME");
                out.println(s);
            }

            out.close();
         }
         catch (SQLException e) {

            PrintWriter out = res.getWriter();
            out.println("ERR " + e.getMessage());
            out.close();
        }
    }
    catch(ClassNotFoundException e) {

        PrintWriter out = res.getWriter();
        out.println("ERR " + e.getMessage());
        out.close();
    }
    
  }
}