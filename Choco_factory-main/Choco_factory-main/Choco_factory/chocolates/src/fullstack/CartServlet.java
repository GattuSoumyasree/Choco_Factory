package fullstack;

import fullstack.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");

        if (productId != null && !productId.isEmpty() && productName != null && !productName.isEmpty() && productPrice != null && !productPrice.isEmpty()) {
            Product product = new Product(productId, productName, productPrice);
            cart.add(product);
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        String removeProductId = request.getParameter("removeProductId");
        if (removeProductId != null && !removeProductId.isEmpty()) {
            cart.removeIf(product -> product.getId().equals(removeProductId));
        }

        session.setAttribute("cart", cart);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/594_schema", "root", "7104");

            String productIds = cart.stream().map(Product::getId).reduce((id1, id2) -> id1 + "," + id2).orElse("-1");

            String query = "SELECT * FROM products WHERE id IN (" + productIds + ")";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Shopping Cart</title>");
            out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
            out.println("<style>");
            // CSS styles
            out.println(".logo { width: 150px; height: auto; }");
            out.println("#logo-img { width: 65%; height: 65%; display: block; }");
            out.println("#cart-count { position: absolute; top: 10px; right: 40px; background-color: red; color: white; border-radius: 50%; padding: 3px 5px; font-size: 12px; }");
            out.println(".cart-item { display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid #ccc; padding: 15px 0; }");
            out.println(".cart-item img { width: 200px; height: auto; }");
            out.println(".cart-item-name { font-size: 20px; }");
            out.println(".cart-item-price { font-size: 18px; color: #555; }");
            out.println(".cart-item-remove { font-size: 18px; color: red; cursor: pointer; }");
            out.println(".button-container { text-align: center; margin-top: 20px; }");
            out.println(".button { padding: 15px 30px; background-color: #8B4513; color: white; border: none; border-radius: 8px; font-size: 18px; cursor: pointer; text-transform: uppercase; display: inline-block; }");
            out.println(".button:hover { background-color: #A0522D; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Header and navigation bar
            out.println("<header>");
            out.println("<div class='logo'><img src='logo.jpg' alt='Chocolate Fusion' id='logo-img'></div>");
            out.println("<div class='search-bar'><input type='text' id='search-input' placeholder='Search for gifts' oninput='filterProducts()'><button onclick='filterProducts()'>Search</button></div>");
            out.println("<div class='user-options'><a href='del.html'><i class='fas fa-map-marker-alt'></i> Delivery Location</a><a href='login.jsp'><i class='fas fa-user'></i></a><a href='cart' id='cart-icon'><span id='cart-count'>" + cart.size() + "</span><i class='fas fa-shopping-cart'></i></a></div>");
            out.println("</header>");
            // Navigation menu
            out.println("<nav class='categories'>");
            out.println("<a href='#'>EXPRESS</a>");
            out.println("<a href='#'>CAKES</a>");
            out.println("<a href='#'>FLOWERS</a>");
            out.println("<a href='#'>PLANTS</a>");
            out.println("<a href='#'>GIFTS</a>");
            out.println("<a href='#'>PERSONALIZED GIFTS</a>");
            out.println("<div class='dropdown'>");
            out.println("<a href='#'>CHOCOLATES</a>");
            out.println("<div class='dropdown-content'>");
            out.println("<div class='dropdown-column'>");
            out.println("<h4>By Type</h4>");
            out.println("<a href='all.html'>All Chocolates</a>");
            out.println("<a href='best.html'>Best Seller Chocolates</a>");
            out.println("<a href='personalized.html'>Personalized Chocolates</a>");
            out.println("<a href='bouquet.html'>Chocolate Bouquet</a>");
            out.println("<a href='premium.html'>Premium chocolates</a>");
            out.println("<a href='hampers.html'>Chocolate Hampers</a>");
            out.println("</div>");
            out.println("<div class='dropdown-column'>");
            out.println("<h4>By Flavor</h4>");
            out.println("<a href='dark.html'>Dark Chocolates</a>");
            out.println("<a href='milk.html'>Milk Chocolates</a>");
            out.println("<a href='white.html'>White Chocolates</a>");
            out.println("<a href='sugarfree.html'>Sugar Free Chocolates</a>");
            out.println("<a href='handmade.html'>Hand Made Chocolates</a>");
            out.println("</div>");
            out.println("<div class='dropdown-column'>");
            out.println("<h4>By Brand</h4>");
            out.println("<a href='winni.html'>Winni Chocolates</a>");
            out.println("<a href='page2.html'>Cadbury Chocolates</a>");
            out.println("<a href='page1.html'>Ferrero rocher Chocolates</a>");
            out.println("<a href='justrufs.html'>Jus trufs Chocolates</a>");
            out.println("<a href='Velvet.html'>Velvet Fine Chocolates</a>");
            out.println("</div>");
            out.println("<div class='dropdown-column'>");
            out.println("<h4>By Combos</h4>");
            out.println("<a href='combos.html'>Chocolate Combos</a>");
            out.println("<a href='cakes.html'>Chocolate And Cakes</a>");
            out.println("<a href='flowers.html'>Chocolate And Flowers</a>");
            out.println("<h4>By Occasions</h4>");
            out.println("<a href='birthday.html'>Birthday Chocolates</a>");
            out.println("<a href='anniversary.html'>Anniversary Chocolates</a>");
            out.println("<a href='wedding.html'>Wedding Chocolates</a>");
            out.println("<a href='valentine.html'>Valentine Chocolates</a>");
            out.println("<a href='mothersday.html'>Mothers Day Chocolates</a>");
            out.println("<a href='fathersday.html'>Fathers Day Chocolates</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</nav>");

            out.println("<div id='product-container'>");

            if (cart.isEmpty()) {
                out.println("<h3>Your cart is empty.</h3>");
            } else {
                out.println("<h2>Shopping Cart</h2>");
                for (Product product : cart) {
                    out.println("<div class='cart-item'>");
                    out.println("<div class='cart-item-name'>" + product.getName() + "</div>");
                    out.println("<div class='cart-item-price'>₹" + product.getPrice() + "</div>");
                    out.println("<a href='cart?removeProductId=" + product.getId() + "' class='cart-item-remove'>Remove</a>");
                    out.println("</div>");
                }
                out.println("<div class='button-container'>");
                out.println("<button class='button'>Proceed to Checkout</button>");
                out.println("</div>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
