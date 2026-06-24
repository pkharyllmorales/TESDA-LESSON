import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class GCashServer {
    
    private static final int PORT = 8080;
    private static Map<Integer, User> usersDatabase = new HashMap<>();
    private static int userIdCounter = 1001;
    private static Integer currentLoggedInUserId = null;
    
    static class User {
        int id;
        String name;
        String email;
        String number;
        String pin;
        
        User(int id, String name, String email, String number, String pin) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.number = number;
            this.pin = pin;
        }
    }
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        server.createContext("/", exchange -> serveHome(exchange));
        server.createContext("/register", exchange -> serveRegister(exchange));
        server.createContext("/login", exchange -> serveLogin(exchange));
        server.createContext("/dashboard", exchange -> serveDashboard(exchange));
        server.createContext("/api/register", exchange -> handleRegister(exchange));
        server.createContext("/api/login", exchange -> handleLogin(exchange));
        server.createContext("/api/changepin", exchange -> handleChangePin(exchange));
        server.createContext("/logout", exchange -> handleLogout(exchange));
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("================================");
        System.out.println("🏦 GCash Server Started!");
        System.out.println("================================");
        System.out.println("Open: http://localhost:" + PORT);
        System.out.println("================================");
    }
    
    private static void serveHome(HttpExchange exchange) throws IOException {
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>GCash - Online Banking</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { font-family: 'Segoe UI', Arial, sans-serif; 
                           background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                           min-height: 100vh; display: flex; align-items: center; 
                           justify-content: center; }
                    .container { background: white; padding: 40px; border-radius: 10px;
                                box-shadow: 0 10px 25px rgba(0,0,0,0.2); max-width: 500px; 
                                width: 90%; text-align: center; }
                    h1 { color: #333; margin-bottom: 10px; }
                    .subtitle { color: #666; margin-bottom: 30px; }
                    .button-group { display: flex; gap: 15px; margin-top: 30px; }
                    a { flex: 1; padding: 12px 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white; text-decoration: none; border-radius: 5px; font-weight: bold;
                        transition: all 0.3s ease; }
                    a:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4); }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🏦 GCash</h1>
                    <p class="subtitle">Online Banking System</p>
                    <div class="button-group">
                        <a href="/login">Login</a>
                        <a href="/register">Register</a>
                    </div>
                </div>
            </body>
            </html>
            """;
        
        sendResponse(exchange, html);
    }
    
    private static void serveRegister(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }
        
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Register - GCash</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { font-family: 'Segoe UI', Arial, sans-serif; 
                           background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                           min-height: 100vh; display: flex; align-items: center; justify-content: center; }
                    .container { background: white; padding: 40px; border-radius: 10px;
                                box-shadow: 0 10px 25px rgba(0,0,0,0.2); max-width: 400px; width: 90%; }
                    h1 { color: #333; margin-bottom: 30px; text-align: center; }
                    .form-group { margin-bottom: 20px; }
                    label { display: block; margin-bottom: 8px; color: #333; font-weight: 500; }
                    input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px;
                           font-size: 14px; }
                    input:focus { outline: none; border-color: #667eea; }
                    button { width: 100%; padding: 12px; margin-top: 20px;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: white; border: none; border-radius: 5px; 
                            font-weight: bold; cursor: pointer; }
                    button:hover { transform: translateY(-2px); }
                    .login-link { text-align: center; margin-top: 20px; color: #666; }
                    a { color: #667eea; text-decoration: none; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Register</h1>
                    <form method="POST" action="/api/register">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" name="name" required>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" required>
                        </div>
                        <div class="form-group">
                            <label>Phone Number</label>
                            <input type="tel" name="number" placeholder="09123456789" required>
                        </div>
                        <div class="form-group">
                            <label>PIN (4-6 digits)</label>
                            <input type="password" name="pin" required>
                        </div>
                        <button type="submit">Create Account</button>
                    </form>
                    <div class="login-link">
                        Already have account? <a href="/login">Login here</a>
                    </div>
                </div>
            </body>
            </html>
            """;
        
        sendResponse(exchange, html);
    }
    
    private static void serveLogin(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }
        
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Login - GCash</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { font-family: 'Segoe UI', Arial, sans-serif; 
                           background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                           min-height: 100vh; display: flex; align-items: center; justify-content: center; }
                    .container { background: white; padding: 40px; border-radius: 10px;
                                box-shadow: 0 10px 25px rgba(0,0,0,0.2); max-width: 400px; width: 90%; }
                    h1 { color: #333; margin-bottom: 30px; text-align: center; }
                    .form-group { margin-bottom: 20px; }
                    label { display: block; margin-bottom: 8px; color: #333; font-weight: 500; }
                    input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px;
                           font-size: 14px; }
                    input:focus { outline: none; border-color: #667eea; }
                    button { width: 100%; padding: 12px; margin-top: 20px;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: white; border: none; border-radius: 5px; 
                            font-weight: bold; cursor: pointer; }
                    button:hover { transform: translateY(-2px); }
                    .register-link { text-align: center; margin-top: 20px; color: #666; }
                    a { color: #667eea; text-decoration: none; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🔐 Login</h1>
                    <form method="POST" action="/api/login">
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" required autofocus>
                        </div>
                        <div class="form-group">
                            <label>PIN</label>
                            <input type="password" name="pin" required>
                        </div>
                        <button type="submit">Login</button>
                    </form>
                    <div class="register-link">
                        Don't have account? <a href="/register">Register here</a>
                    </div>
                </div>
            </body>
            </html>
            """;
        
        sendResponse(exchange, html);
    }
    
    private static void serveDashboard(HttpExchange exchange) throws IOException {
        if (currentLoggedInUserId == null) {
            redirect(exchange, "/login");
            return;
        }
        
        User user = usersDatabase.get(currentLoggedInUserId);
        
        String html = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Dashboard - GCash</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { font-family: 'Segoe UI', Arial, sans-serif;
                           background: linear-gradient(135deg, #667eea 0%, #764ba2 100%%);
                           min-height: 100vh; padding: 20px; }
                    .container { max-width: 600px; margin: 0 auto; }
                    .header { background: white; padding: 20px; border-radius: 10px;
                             box-shadow: 0 10px 25px rgba(0,0,0,0.2); 
                             display: flex; justify-content: space-between; 
                             align-items: center; margin-bottom: 20px; }
                    .card { background: white; padding: 30px; border-radius: 10px;
                           box-shadow: 0 10px 25px rgba(0,0,0,0.2); margin-bottom: 20px; }
                    h2 { color: #333; margin-bottom: 20px; padding-bottom: 15px;
                        border-bottom: 2px solid #667eea; }
                    .info-item { display: flex; justify-content: space-between;
                               padding: 10px 0; border-bottom: 1px solid #eee; }
                    .label { font-weight: bold; color: #666; }
                    .form-group { margin-bottom: 20px; }
                    label { display: block; margin-bottom: 8px; font-weight: bold; }
                    input { width: 100%%; padding: 12px; border: 1px solid #ddd;
                           border-radius: 5px; }
                    button { width: 100%%; padding: 12px; margin-top: 10px;
                            background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                            color: white; border: none; border-radius: 5px;
                            font-weight: bold; cursor: pointer; }
                    .logout-btn { background: #dc3545; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🏦 Dashboard</h1>
                        <form method="POST" action="/logout">
                            <button type="submit" class="logout-btn">Logout</button>
                        </form>
                    </div>
                    
                    <div class="card">
                        <h2>👤 Account Information</h2>
                        <div class="info-item">
                            <span class="label">User ID:</span>
                            <span>%d</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Name:</span>
                            <span>%s</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Email:</span>
                            <span>%s</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Phone:</span>
                            <span>%s</span>
                        </div>
                    </div>
                    
                    <div class="card">
                        <h2>🔐 Change PIN</h2>
                        <form method="POST" action="/api/changepin">
                            <input type="hidden" name="userId" value="%d">
                            <div class="form-group">
                                <label>Current PIN</label>
                                <input type="password" name="oldPin" required>
                            </div>
                            <div class="form-group">
                                <label>New PIN (4-6 digits)</label>
                                <input type="password" name="newPin" required>
                            </div>
                            <button type="submit">Update PIN</button>
                        </form>
                    </div>
                </div>
            </body>
            </html>
            """, user.id, user.name, user.email, user.number, currentLoggedInUserId);
        
        sendResponse(exchange, html);
    }
    
    private static void handleRegister(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }
        
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> params = parseParams(body);
        
        String name = params.get("name");
        String email = params.get("email");
        String number = params.get("number");
        String pin = params.get("pin");
        
        // Validation with error messages
        if (name == null || !validateName(name)) {
            redirect(exchange, "/register?error=Name+must+be+at+least+3+characters");
            return;
        }
        
        if (email == null || !validateEmail(email)) {
            redirect(exchange, "/register?error=Invalid+email+format");
            return;
        }
        
        // Check duplicate email
        for (User u : usersDatabase.values()) {
            if (u.email.equals(email)) {
                redirect(exchange, "/register?error=Email+already+registered");
                return;
            }
        }
        
        if (number == null || !validateNumber(number)) {
            redirect(exchange, "/register?error=Phone+number+must+be+10-11+digits");
            return;
        }
        
        if (pin == null || !validatePin(pin)) {
            redirect(exchange, "/register?error=PIN+must+be+4-6+digits");
            return;
        }
        
        // Create user
        int userId = userIdCounter++;
        usersDatabase.put(userId, new User(userId, name, email, number, pin));
        
        redirect(exchange, "/login?success=Registration+successful!+Please+login");
    }
    
    private static void handleLogin(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
            return;
        }
        
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> params = parseParams(body);
        
        String email = params.get("email");
        String pin = params.get("pin");
        
        for (User user : usersDatabase.values()) {
            if (user.email.equals(email) && user.pin.equals(pin)) {
                currentLoggedInUserId = user.id;
                redirect(exchange, "/dashboard");
                return;
            }
        }
        
        redirect(exchange, "/login");
    }
    
    private static void handleChangePin(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST") || currentLoggedInUserId == null) {
            redirect(exchange, "/login");
            return;
        }
        
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> params = parseParams(body);
        
        int userId = Integer.parseInt(params.get("userId"));
        String oldPin = params.get("oldPin");
        String newPin = params.get("newPin");
        
        User user = usersDatabase.get(userId);
        if (user != null && user.pin.equals(oldPin) && validatePin(newPin)) {
            user.pin = newPin;
        }
        
        redirect(exchange, "/dashboard");
    }
    
    private static void handleLogout(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            currentLoggedInUserId = null;
        }
        redirect(exchange, "/");
    }
    
    private static boolean validateName(String name) {
        return name != null && name.length() >= 3;
    }
    
    private static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }
    
    private static boolean validateNumber(String number) {
        return number != null && number.matches("\\d{10,11}");
    }
    
    private static boolean validatePin(String pin) {
        return pin != null && pin.matches("\\d{4,6}");
    }
    
    private static Map<String, String> parseParams(String body) {
        Map<String, String> params = new HashMap<>();
        if (body != null && !body.isEmpty()) {
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    try {
                        params.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                                  URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        params.put(kv[0], kv[1]);
                    }
                }
            }
        }
        return params;
    }
    
    private static void sendResponse(HttpExchange exchange, String html) throws IOException {
        byte[] response = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }
    
    private static void redirect(HttpExchange exchange, String location) throws IOException {
        exchange.getResponseHeaders().set("Location", location);
        exchange.sendResponseHeaders(302, 0);
        exchange.close();
    }
}
