package fullstack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/productDetails")
public class ProductDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/594_schema", "root", "7104");

            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(productId));

            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product Details</title>");
            out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
            out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css'>");
            out.println("<style>");
            // CSS styles
            out.println(".logo { width: 150px; height: auto; }");
            out.println("#logo-img { width: 65%; height: 65%; display: block; }");
            out.println("#cart-count { position: absolute; top: 10px; right: 35px; background-color: red; color: white; border-radius: 50%; padding: 3px 5px; font-size: 12px; }");
            out.println(".product-price { font-size: 24px; font-weight: bold; }");
            out.println(".product-name { font-size: 30px; font-weight: bold; }");
            out.println(".cart-item { display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid #ccc; padding: 15px 0; }");
            out.println(".cart-item img { width: 200px; height: 200px; }");
            out.println(".cart-item-name { font-size: 20px; }");
            out.println(".cart-item-price { font-size: 18px; color: #555; }");
            out.println(".button-container { text-align: center; margin-top: 20px; }");
            out.println(".button { padding: 15px 30px; background-color: #8B4513; color: white; border: none; border-radius: 8px; font-size: 18px; cursor: pointer; text-transform: uppercase; display: inline-block; }");
            out.println(".button:hover { background-color: #A0522D; }");
            out.println(".product-image { transition: transform 0.2s; }");
            out.println(".product-image:hover { transform: scale(1.5); }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Header and navigation bar
            out.println("<header>");
            out.println("<div class='logo'><img src='logo.jpg' alt='Chocolate Fusion' id='logo-img'></div>");
            out.println("<div class='search-bar'><input type='text' id='search-input' placeholder='Search for gifts' oninput='filterProducts()'><button onclick='filterProducts()'>Search</button></div>");
            out.println("<div class='user-options'><a href='del.html'><i class='fas fa-map-marker-alt'></i> Delivery Location</a><a href='cart' id='cart-icon'><span id='cart-count'>0</span><i class='fas fa-shopping-cart'></i></a><a href='login.jsp'><i class='fas fa-user'></i></a></div>");
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
            out.println("<a href='bouquet.html'>Chocolate B                                                                                                                                                                           ouquet</a>");
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
            out.println("<a href='birth.html'>Birthday Chocolate</a>");
            out.println("<a href='anni.html'>Anniversary Chocolate</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("<a href='#'>COMBOS</a>");
            out.println("<a href='#'>BIRTHDAY</a>");
            out.println("<a href='#'>ANNIVERSARY</a>");
            out.println("<a href='#'>OCCASIONS</a>");
            out.println("</nav>");

            // Product details
            if (rs.next()) {
                out.println("<div class='product-container'>");
                out.println("<center>");
                out.println("<div class='product-name'>" + rs.getString("name") + "</div>");
                out.println("<img class='product-image' src='" + rs.getString("image_url") + "' alt='" + rs.getString("name") + "' />");
                out.println("<div class='product-price'>Price: $" + rs.getDouble("price") + "</div>");
                out.println("<form action='cart' method='post'>");
                out.println("<input type='hidden' name='productId' value='" + productId + "' />");
                out.println("<button class='button' type='submit'>Add to Cart</button>");
                out.println("</center>");
                out.println("</form>");
                out.println("</div>");
            } else {
                out.println("<p>No product found with the given ID.</p>");
            }

         // Footer content
            out.println("<br>");
            out.println("<footer>");
            out.println("<p>Indulge in the rich, creamy goodness of white chocolates with Winni! Our collection offers an array of premium quality white chocolates, perfect for every occasion. Whether you're treating yourself or gifting a loved one, our white chocolates promise a melt-in-your-mouth experience that will leave you craving for more.</p>");
            out.println("<p>Why Choose Winni for Your White Chocolate Cravings?</p>");
            out.println("<ul>");
            out.println("<li><strong>Wide Range of Selection:</strong> From classic white chocolate bars to unique flavored options, our selection is curated to satisfy every palate.</li>");
            out.println("<li><strong>Premium Quality:</strong> We source our chocolates from top-notch brands ensuring you get nothing but the best.</li>");
            out.println("<li><strong>Perfect for Gifting:</strong> Packaged elegantly, our white chocolates make for an ideal gift for birthdays, anniversaries, or any special occasion.</li>");
            out.println("<li><strong>Convenient Online Shopping:</strong> Browse through our collection and place your order from the comfort of your home. Fast delivery guaranteed!</li>");
            out.println("</ul>");
            out.println("<p>At Winni, we believe that every moment is a celebration and our white chocolates add the perfect touch of sweetness to your special moments. So why wait? Dive into the world of delicious white chocolates and make your occasions memorable with Winni.</p>");
            out.println("<p>Order your favorite white chocolates online today and experience the joy of indulging in the finest treats!</p>");
            out.println("<div class='footer-container'>");
            out.println("<div class='footer-section'><h3>Our Company</h3><ul>");
            out.println("<li><a href='#'>About Us</a></li>");
            out.println("<li><a href='#'>Careers</a></li>");
            out.println("<li><a href='#'>Contact Us</a></li>");
            out.println("<li><a href='#'>Affiliate Program</a></li>");
            out.println("<li><a href='#'>In News</a></li>");
            out.println("</ul></div>");
            out.println("<div class='footer-section'><h3>Quick Links</h3><ul>");
            out.println("<li><a href='#'>Wishes</a></li>");
            out.println("<li><a href='#'>Sitemap</a></li>");
            out.println("<li><a href='#'>Customer Reviews</a></li>");
            out.println("<li><a href='#'>Blog - Celebrate Relations</a></li>");
            out.println("<li><a href='#'>Corporate Order</a></li>");
            out.println("<li><a href='#'>Franchise Enquiry</a></li>");
            out.println("</ul></div>");
            out.println("<div class='footer-section'><h3>Policy & Security</h3><ul>");
            out.println("<li><a href='#'>FAQ</a></li>");
            out.println("<li><a href='#'>Refund Policy</a></li>");
            out.println("<li><a href='#'>Privacy Policy</a></li>");
            out.println("<li><a href='#'>Bug Bounty</a></li>");
            out.println("<li><a href='#'>Data Security</a></li>");
            out.println("<li><a href='#'>Cancellation Policy</a></li>");
            out.println("<li><a href='#'>Terms and Conditions</a></li>");
            out.println("<li><a href='#'>Payments and Security</a></li>");
            out.println("</ul></div>");
            out.println("<div class='footer-social'><h3>Connect with Us</h3>");
            out.println("<a href='https://www.facebook.com/WinniGifts'><img src='https://assets.winni.in/groot/2023/07/19/desktop/facebook.png' alt='Facebook'></a>");
            out.println("<a href='https://www.instagram.com/winnigifts/'><img src='https://assets.winni.in/groot/2023/07/19/desktop/instagram.png' alt='Instagram'></a>");
            out.println("<a href='https://twitter.com/winni_gifts'><img src='https://assets.winni.in/groot/2023/12/14/desktop/twiter.png' alt='Twitter'></a>");
            out.println("<a href='https://www.linkedin.com/company/winni'><img src='https://assets.winni.in/groot/2023/07/19/desktop/linkdin.png' alt='LinkedIn'></a>");
            out.println("<a href='https://www.youtube.com/channel/UCsXnWQIHuO3DOr-AhtftD2w'><img src='https://assets.winni.in/groot/2023/07/19/desktop/youtube.png' alt='YouTube'></a>");
            out.println("<a href='https://www.whatsapp.com/channel/0029VaAqyET72WTsWYuAqe3Z'><img src='https://assets.winni.in/groot/2024/04/23/mobile/black-whatsapp-icon.png' alt='WhatsApp'></a>");
            out.println("</div>");
            out.println("<div class='footer-app'><p>Experience Winni on mobile</p>");
            out.println("<a href='https://play.google.com/store/apps/details?id=in.winni.app&referrer=utm_source%3Dwinni-website%26utm_medium%3Dweb-link'><img src='https://assets.winni.in/groot/2023/07/19/desktop/google-play.png' alt='Google Play'></a>");
            out.println("<a href='https://apps.apple.com/in/app/winni-cake-flowers-gifts/id1080301515'><img src='https://assets.winni.in/groot/2023/07/19/desktop/app-store.png' alt='App Store'></a>");
            out.println("</div>");
            out.println("<div class='footer-bottom'><p>&copy; 2013-2024 Winni.in. All Rights Reserved</p>");
            out.println("<p>Company Name: Dhawala Online Solutions Private Limited | CIN: U51109KA2012PTC065653 | Regd. Office Address: 3rd Floor, PLOT. NO # 128/P2, EPIP Industrial Area Whitefield, Sonnenahalli Village, Bangalore - 560066 | Contact no. +91 - 7829463510 | E-mail: info@winni.in</p>");
            out.println("</div>");
            out.println("</div>");
            out.println("</footer>");

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
