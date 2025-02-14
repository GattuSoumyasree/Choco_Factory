package fullstack;

import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ConnectionServlet")
public class ConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ConnectionServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            registerUser(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            loginUser(request, response);
        } else {
            response.getWriter().println("Invalid action!");
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uemail = request.getParameter("reg-email");
        String upassword = request.getParameter("reg-password");
        String umobile = request.getParameter("reg-mobile");

        // Check if the phone number is more than 10 digits
        if (umobile.length() > 10) {
            response.setContentType("text/html");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Invalid phone number: It should be 10 digits');");
            response.getWriter().println("window.location.href = 'login.jsp';"); // replace with your registration page
            response.getWriter().println("</script>");
            return;
        }

        Connection con = null;
        PreparedStatement pre = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/594_schema", "root", "7104");
            String sql = "INSERT INTO user (email, password, mobile) VALUES (?, ?, ?)";
            pre = con.prepareStatement(sql);
            pre.setString(1, uemail);
            pre.setString(2, upassword);
            pre.setString(3, umobile);
            pre.executeUpdate();
            
            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Error registering user: " + e.getMessage() + "');");
            response.getWriter().println("window.location.href = 'login.jsp';"); // replace with your registration page
            response.getWriter().println("</script>");
        } finally {
            try {
                if (pre != null) pre.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uemail = request.getParameter("email");
        String upassword = request.getParameter("password");

        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/594_schema", "root", "7104");
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            pre = con.prepareStatement(sql);
            pre.setString(1, uemail);
            pre.setString(2, upassword);
            rs = pre.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", uemail);
                response.sendRedirect("page1.html");
            } else {
                response.setContentType("text/html");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('Invalid email or password!');");
                response.getWriter().println("window.location.href = 'login.jsp';");
                response.getWriter().println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('Error logging in: " + e.getMessage() + "');");
            response.getWriter().println("window.location.href = 'login.jsp';");
            response.getWriter().println("</script>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pre != null) pre.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
