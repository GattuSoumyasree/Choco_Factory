package fullstack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");

        // Validate ZIP code
        if (!zip.matches("\\d{1,7}")) {
            response.setContentType("text/html");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Invalid ZIP code: It should be a number with 7 digits or less.');");
            response.getWriter().println("window.history.back();");
            response.getWriter().println("</script>");
            return;
        }

        // JDBC variables
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Connect to your MySQL database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/594_schema", "root", "7104");

            // SQL query to insert data into billing_info table
            String sql = "INSERT INTO billing_info (full_name, email, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setString(4, city);
            stmt.setString(5, state);
            stmt.setString(6, zip);

            // Execute the query
            stmt.executeUpdate();

            // Redirect to the confirmation page
            response.sendRedirect("payment.html");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the PreparedStatement and Connection
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
