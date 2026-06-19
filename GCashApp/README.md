# GCash Online Banking - Spring Boot Web Application

A complete online banking system with user authentication built with Spring Boot and Thymeleaf.

## 📋 Features

✅ User Registration with validation
- Name (minimum 3 characters)
- Email (valid format, no duplicates)
- Phone Number (10-11 digits)
- PIN (4-6 digits)

✅ User Login
- Email and PIN authentication
- Secure session management
- Account activation status check

✅ User Dashboard
- View account information
- Change PIN with verification

✅ Logout
- Secure session termination

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6+

## 📦 Installation

1. Navigate to the GCashApp directory:
```bash
cd GCashApp
```

2. Install dependencies:
```bash
mvn clean install
```

## ▶️ Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using Java
```bash
mvn clean package
java -jar target/gcash-app-1.0.0.jar
```

## 🌐 Access the Application

Once running, open your browser and navigate to:
```
http://localhost:8080
```

## 📝 Usage

### 1. Register a New Account
- Click "Register" button
- Fill in your details:
  - Full Name (3+ characters)
  - Email (valid format)
  - Phone Number (10-11 digits)
  - PIN (4-6 digits)
- Submit to create account

### 2. Login
- Click "Login" button
- Enter your email and PIN
- Access your dashboard

### 3. Dashboard
- View your account information
- Change your PIN (requires current PIN verification)
- Click Logout to exit

## 📁 Project Structure

```
GCashApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/gcash/
│   │   │       ├── GCashApplication.java      (Main application)
│   │   │       ├── controller/
│   │   │       │   └── AuthController.java    (HTTP handlers)
│   │   │       └── service/
│   │   │           └── UserAuthentication.java (Business logic)
│   │   └── resources/
│   │       ├── application.properties         (Configuration)
│   │       └── templates/
│   │           ├── index.html                 (Homepage)
│   │           ├── register.html              (Registration)
│   │           ├── login.html                 (Login)
│   │           └── dashboard.html             (User Dashboard)
└── pom.xml                                     (Maven dependencies)
```

## 🔒 Security Notes

- PINs are used as passwords (in production, use bcrypt/hashing)
- User database is in-memory (uses HashMap)
- For production: implement database persistence, encryption, and session security

## 🧪 Test Accounts

After registration:
- Use your registered email and PIN to login
- Example:
  - Email: john@gmail.com
  - PIN: 1234

## 🚀 Building for Production

```bash
mvn clean package
```

This creates a JAR file in the `target/` directory that can be deployed.

## 📞 Support

For issues or questions, check the application logs and ensure:
- Port 8080 is available
- Java and Maven are properly installed
- All dependencies are downloaded

---

**GCash Online Banking v1.0**
