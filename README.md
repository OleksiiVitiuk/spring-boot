# Bookstore

Welcome to Bookstore — a modern, secure, and scalable online book shopping platform. This is more than just a CRUD project — it’s an e-commerce-ready solution tailored for clean architecture, real-world business logic, and extensibility.

✨ Core Functionality
🛒 User Registration & Authentication
Secure sign-up/login flows with role-based access control (admin/user) using Spring Security & JWT.

📚 Browse & Search Books
Instantly view available books, filter by category, and search by title or author.

🧠 Smart Sorting & Filtering
Sort by price, name, or popularity. Use categories to quickly narrow your choices.

🛍️ Add to Cart & Manage Cart Items
Users can build their cart intuitively and manage quantities in real time.

📦 Order Placement
Seamless checkout experience. Place orders with calculated totals and delivery info.

📜 Order History Tracking
View past orders with statuses like PENDING, COMPLETED, and DELIVERED.

🧑‍💼 Admin Panel Features
Admins can manage inventory — create, update, or soft-delete books and categories.

💡 Why You’ll Love This Project
✅ Built with Clean Architecture
Clearly separated layers: Entity, Repository, Service, Controller.

💾 Database-Safe Design
Soft delete implemented across all major entities using Hibernate filters.

🔐 Secure by Default
JWT authentication, role-based access, and user isolation — out of the box.

🧪 Test-Friendly
Designed with testability in mind — use TDD or write integration tests easily.

🔍 Swagger UI Enabled
Explore all API endpoints visually at
http://localhost:8080/swagger-ui/index.html

**Model diagram:**
![img.png](README_files/img.png)


The development of the Bookstore application involved the following technologies:

![img_2.png](README_files/img_2.png)

**Functional**

User capabilities:
1. Register and log in.
2. View all books, sort them by category, or search by title/author/isbn.
3. Add books to the cart, update or remove them.
4. Create an order and track its status.

Admin capabilities:
1. Create, update, or delete book categories and books.
2. Soft deletion is enabled, allowing you to restore deleted items if needed.

**Book-market`s endpoints:**
![img_1.png](README_files/img_1.png)

	Auth (/auth):
            GET: /registration - Register a new user
            GET: /login - Retrieve a token for Bearer authentication

	Book (/books):
            GET: / - Retrieve a list of books
            POST: / - Add a new book (ADMIN only)
            PUT: /{id} - Update a book by ID (ADMIN only)
            DELETE: /{id} - Soft delete a book (ADMIN only)
            GET: /search - Search for books using parameters

	Categories (/categories):
            GET: / - Retrieve a paginated list of categories
            POST: / - Create a new category (ADMIN only)
            PUT: /{id} - Update a category by ID (ADMIN only)
            GET: /{id} - Retrieve a category by ID
            DELETE: /{id} - Soft delete a category (ADMIN only)
            GET: /{id}/books - Retrieve books by category

	Cart (/cart):
            GET: / - Retrieve the authenticated user's cart
            POST: / - Add an item to the cart
            PUT: /item/{cartItemId} - Update the quantity of an item in the cart
            DELETE: /item/{cartItemId} - Soft delete an item from the cart

	Orders (/orders):
            GET: / - Retrieve a paginated list of user orders
            GET: /{id}/items - Retrieve items in a user's order
            GET: /{id}/items/{orderId} - Retrieve a specific item from an order
            POST: / - Create a new order
            PATCH: /{id} - Update order status (ADMIN only)

**Swagger UI:**
![img_3.png](README_files/img_3.png)

![swagger_demo.gif](README_files/swagger_demo.gif)

**Running Book-market:**

Docker:
1. Install Docker Desktop (if not installed).

2. Configure the .env file with your settings.

3. Open the terminal and run the following commands:

`docker-compose build  # Build the images `

`docker-compose up     # Start the project`

`docker-compose down   # Stop the project`

Running Locally
1. Open application.properties.

2. Set the required database properties:
    * Database URL
    * Username
    * Password

3. Press "run"
4. After starting the web server, download books.postman_collection.json(in README_files).