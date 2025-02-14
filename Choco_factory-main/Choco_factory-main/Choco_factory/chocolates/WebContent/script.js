// Filter products based on search input and price filter
function filterProducts() {
    const searchInput = document.getElementById('search-input').value.toLowerCase();
    const filterPrice = document.getElementById('filter-price').value;
    const products = document.querySelectorAll('.product');
    products.forEach(product => {
        const name = product.dataset.name.toLowerCase();
        const price = parseInt(product.dataset.price);
        let priceMatch = true;
        if (filterPrice === 'under-1000') {
            priceMatch = price < 1000;
        } else if (filterPrice === '1000-5000') {
            priceMatch = price >= 1000 && price <= 5000;
        } else if (filterPrice === 'above-5000') {
            priceMatch = price > 5000;
        }
        if (name.includes(searchInput) && priceMatch) {
            product.style.display = 'block';
        } else {
            product.style.display = 'none';
        }
    });
}

// Sort products based on selected criteria
function sortProducts() {
    const sortBy = document.getElementById('sort-by').value;
    const products = Array.from(document.querySelectorAll('.product'));
    const container = document.getElementById('products');
    products.sort((a, b) => {
        const priceA = parseInt(a.dataset.price);
        const priceB = parseInt(b.dataset.price);
        if (sortBy === 'low-to-high') {
            return priceA - priceB;
        } else if (sortBy === 'high-to-low') {
            return priceB - priceA;
        } else if (sortBy === 'popularity') {
            // Placeholder for popularity sorting logic
            return 0;
        } else if (sortBy === 'newest-first') {
            // Placeholder for newest first sorting logic
            return 0;
        }
    });
    container.innerHTML = '';
    products.forEach(product => container.appendChild(product));
}

// Handle viewing product details and storing in local storage
function viewProductDetails(name, price, image) {
    localStorage.setItem('productDetails', JSON.stringify({ name, price, image }));
    window.location.href = 'product-details.html';
}

// Load product details from local storage
function loadProductDetails() {
    const product = JSON.parse(localStorage.getItem('productDetails'));
    if (product) {
        document.getElementById('product-name').textContent = product.name;
        document.getElementById('product-price').textContent = `₹${product.price}`;
        document.getElementById('product-image').src = product.image;
    }
}

// Add product to cart and store in local storage
function addToCart() {
    const product = JSON.parse(localStorage.getItem('productDetails'));
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.push(product);
    localStorage.setItem('cart', JSON.stringify(cart));
    alert('Product added to cart');
}

// Load cart items from local storage and display
function loadCart() {
    const cartItems = document.getElementById('cart-items');
    const totalPriceElem = document.getElementById('total-price');
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cartItems.innerHTML = '';
    let totalPrice = 0;

    cart.forEach((item, index) => {
        const itemElem = document.createElement('div');
        itemElem.classList.add('cart-item');
        itemElem.innerHTML = `
            <img src="${item.image}" alt="${item.name}">
            <h2>${item.name}</h2>
            <p>₹${item.price}</p>
            <button onclick="removeFromCart(${index})">Remove</button> `;
        cartItems.appendChild(itemElem);
        totalPrice += parseInt(item.price, 10);
    });

    totalPriceElem.textContent = totalPrice;
}

// Update cart display
function updateCartDisplay() {
    loadCart();
}

// Remove item from cart and update local storage
function removeFromCart(index) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartDisplay();
}

// Initialize functions based on page load
window.onload = function() {
    if (window.location.pathname.includes('product-details.html')) {
        loadProductDetails();
    } else if (window.location.pathname.includes('cart.html')) {
        loadCart();
    }
};

// Event listener to update cart display when DOM content is loaded
document.addEventListener('DOMContentLoaded', updateCartDisplay);

//Function to update the cart icon count
function updateCartIconCount() {
    // Get the cart items from localStorage
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    // Update the cart icon with the count of items in the cart
    document.getElementById('cart-count').textContent = cart.length;
}



// Function to add item to the cart
function addItemToCart(item) {
    // Retrieve the cart from localStorage or initialize it
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    // Add the item to the cart
    cart.push(item);

    // Save the updated cart back to localStorage
    localStorage.setItem('cart', JSON.stringify(cart));

    // Update the cart count in localStorage
    localStorage.setItem('cartCount', cart.length);

    // Update the cart icon count
    updateCartIconCount();

    // Alert the user
    alert('Item added to cart!');
}

// Example of how to add an item to the cart (this function can be called on a button click)
function addItem() {
    const item = {
        id: 1,
        name: 'Sample Item',
        price: 100
    };
    addItemToCart(item);
}

// Update the cart icon count on page load
document.addEventListener('DOMContentLoaded', updateCartIconCount);

