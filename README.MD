# Shop Desktop Application

## Project Description

This project is a desktop application for managing products, stock, and stores. It provides functionality for adding, editing, and deleting products, managing stock levels, and moving stock between stores. The application is built using Java Swing for the user interface and Spring Boot for dependency injection and service management.

The application also includes a ConfigPanel component, which allows users to configure database connection settings. It supports multiple database types (PostgreSQL, MySQL, SQLite) and provides fields for entering host, port, database name, username, and password. Users can test the database connection and save configurations to a properties file. The ConfigPanel ensures flexibility by dynamically updating fields based on the selected database type and preloading existing configurations.


**Location:** Yerevan\
**Date:** 1/2025\
**Developer:** Gagikovich Gor Mkhitaryan

## Technology Stack

### 🛠️ Core Technologies

- **Java Swing**: For building the desktop application's user interface.
- **Spring Boot**: For dependency injection and service management.
- **H2 Database**: Embedded database for storing application data (can be replaced with PostgreSQL or MySQL).
- **Spring Data JPA**: For simplified interaction with the database via ORM.
- **Maven**: For project dependency management and build automation.

### 🗄️ Database

- **H2 Database**: Used for storing data about products, stock, stores, and store stock. The database is embedded, making it easy to set up and use during development.

### 🔒 Security

- **Basic Authentication**: Implemented for securing the application (optional, can be extended with Spring Security).

## Main Features

### 1. Product Management

- **Add Product**: Add new products with details such as code, price, production date, and expiration date.
- **Edit Product**: Modify existing product details.
- **Delete Product**: Remove products from the system.
- **View Products**: Display a list of all products in a table.

### 2. Stock Management

- **Add Stock**: Add stock for a specific product.
- **Remove Stock**: Remove stock for a specific product.
- **Move Stock to Store**: Transfer stock from the main inventory to a store.
- **View Stock**: Display a list of all stock items.

### 3. Store Management

- **Add Store**: Add new stores with a unique name.
- **Edit Store**: Modify store details.
- **Delete Store**: Remove stores from the system.
- **View Stores**: Display a list of all stores.

### 4. Store Stock Management

- **Add Store Stock**: Add stock for a specific product in a store.
- **Remove Store Stock**: Remove stock for a specific product in a store.
- **View Store Stock**: Display a list of all stock items in a specific store.

## Architecture and Component Interaction

### Architecture

- **Java Swing**: Handles the user interface and user interactions.
- **Spring Boot**: Manages dependency injection and service layer logic.
- **H2 Database**: Stores all data related to products, stock, stores, and store stock.
- **Spring Data JPA**: Simplifies database interaction by automatically generating SQL queries for entity management.

### Frontend and Backend Interaction

- The desktop application interacts with the backend services through Spring-managed beans.
- Data is displayed in tables, and user actions (e.g., adding a product) trigger backend service calls to update the database.

## Deployment

### 💻 Local Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/mypy125/shop-desktop.git
   cd shop-desktop
   ```
2. Install dependencies:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Launch the desktop application: The application window will open automatically after the Spring Boot context is initialized.

### 🚀 Cloud Deployment (Optional)

The application can be packaged as a standalone JAR file and deployed to any environment that supports Java.

1. Build the JAR file:
   ```bash
   ./mvnw clean package
   ```
2. Run the JAR file:
   ```bash
   java -jar target/shop-desktop.jar
   ```

## License

This project is licensed under the terms of the GNU General Public License v3.0. See the [LICENSE](https://github.com/mypy125/ecommerce-multivendor-backend/blob/main/LICENSE.txt) file for more details.

## Contribution

We welcome contributions to the project! If you'd like to suggest improvements or fixes, please create a pull request or open an issue.

**Date:** 10/2025\
**Developer:** Gagikovich Gor Mkhitaryan



