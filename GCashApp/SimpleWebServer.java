import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.gcash.service.UserAuthentication;

public class SimpleWebServer {
    
    private static final int PORT = 8080;
    private static HttpServer server;
    private static Map<String, String> sessions = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Routes
        server.createContext("/", new HomeHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/dashboard", new DashboardHandler());
        server.createContext("/changepin", new ChangePinHandler());
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/api/register", new APIRegisterHandler());
        server.createContext("/api/login", new APILoginHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("================================");
        System.out.println("🏦 GCash Web Server Started!");
        System.out.println("================================");
        System.out.println("Open your browser to: http://localhost:" + PORT);
        System.out.println("================================");
    }
    
    static class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>GCash Banking</title>
                    <style>
                        body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                               min-height: 100vh; display: flex; align-items: center; justify-content: center; margin: 0; }
                        .container { background: white; padding: 40px; border-radius: 10px;
                                    box-shadow: 0 10px 25px rgba(0,0,0,0.2); max-width: 500px; text-align: center; }
                        h1 { color: #333; }
                        .button-group { display: flex; gap: 15px; margin-top: 30px; }
                        a { flex: 1; padding: 12px; background: #667eea; color: white; text-decoration: none;
                            border-radius: 5px; font-weight: bold; transition: all 0.3s; }
                        a:hover { transform: translateY(-2px); }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🏦 GCash</h1>
                        <p>Online Banking System</p>
                        <div class="button-group">
                            <a href="/login">Login</a>
                            <a href="/register">Register</a>
                        </div>
                    </div>
                </body>
                </html>
                """;
            
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            exchange.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            exchange.close();
        }
    }
    
    static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("GET")) {
                String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Register - GCash</title>
                        <style>
                            body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                   min-height: 100vh; display: flex; align-items: center; justify-content: center; margin: 0; }
                            .container { background: white; padding: 40px; border-radius: 10px;
                                        box-shadow: 0 10px 25px rgba(0,0,0,0.2); max-width: 400px; width: 100%; }
                            h1 { color: #333; }
                            .form-group { margin-bottom: 20px; }
                            label { display: block; margin-bottom: 8px; font-weight: bold; }
                            input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
                            button { width: 100%; padding: 12px; background: #667eea; color: white;
                                    border: none; border-radius: 5px; font-weight: bold; cursor: pointer; }
                            button:hover { background: #764ba2; }
                            a { color: #667eea; text-decoration: none; }
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
                                    <input type="tel" name="number" required>
                                </div>
                                <div class="form-group">
                                    <label>PIN (4-6 digits)</label>
                                    <input type="password" name="pin" required>
                                </div>
                                <button type="submit">Register</button>
                            </form>
                            <p style="text-align: center; margin-top: 20px;">
                                Already have an account? <a href="/login">Login</a>
                            </p>
                        </div>
                    </body>
                    </html>
                    """;
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
                exchange.close();
            }
        }
    }
    
    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("GET")) {
                String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Login - GCash</title>
                        <style>
                            body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                   min-height: 100vh; display: flex; align-items: center; justify-content: center; margin: 0; }
                            .container { background: white; padding: 40px; border-radius: 10px;
                                        box-shadow: 0 10px 25px rgba(0,0,0,0.2); max-width: 400px; width: 100%; }
                            h1 { color: #333; }
                            .form-group { margin-bottom: 20px; }
                            label { display: block; margin-bottom: 8px; font-weight: bold; }
                            input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
                            button { width: 100%; padding: 12px; background: #667eea; color: white;
                                    border: none; border-radius: 5px; font-weight: bold; cursor: pointer; }
                            button:hover { background: #764ba2; }
                            a { color: #667eea; text-decoration: none; }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h1>🔐 Login</h1>
                            <form method="POST" action="/api/login">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" name="email" required>
                                </div>
                                <div class="form-group">
                                    <label>PIN</label>
                                    <input type="password" name="pin" required>
                                </div>
                                <button type="submit">Login</button>
                            </form>
                            <p style="text-align: center; margin-top: 20px;">
                                Don't have an account? <a href="/register">Register</a>
                            </p>
                        </div>
                    </body>
                    </html>
                    """;
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
                exchange.close();
            }
        }
    }
    
    static class DashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Integer userId = UserAuthentication.getCurrentUserId();
            if (userId == null) {
                exchange.getResponseHeaders().set("Location", "/login");
                exchange.sendResponseHeaders(302, 0);
                exchange.close();
                return;
            }
            
            Map<String, Object> user = UserAuthentication.getUserInfo(userId);
            
            String html = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Dashboard - GCash</title>
                    <style>
                        body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                               min-height: 100vh; padding: 20px; margin: 0; }
                        .container { max-width: 600px; margin: 0 auto; }
                        .card { background: white; padding: 30px; border-radius: 10px;
                               box-shadow: 0 10px 25px rgba(0,0,0,0.2); margin-bottom: 20px; }
                        h2 { color: #333; }
                        .info-item { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #eee; }
                        .label { font-weight: bold; }
                        form { display: grid; gap: 15px; }
                        input { padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
                        button { padding: 12px; background: #667eea; color: white; border: none;
                                border-radius: 5px; font-weight: bold; cursor: pointer; }
                        button:hover { background: #764ba2; }
                        .logout { background: #dc3545; }
                        .logout:hover { background: #c82333; }
                        .header { display: flex; justify-content: space-between; align-items: center; background: white;
                                 padding: 20px; border-radius: 10px; margin-bottom: 20px;
                                 box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🏦 Dashboard</h1>
                            <form method="POST" action="/logout">
                                <button type="submit" class="logout">Logout</button>
                            </form>
                        </div>
                        
                        <div class="card">
                            <h2>👤 Account Information</h2>
                            <div class="info-item">
                                <span class="label">User ID:</span>
                                <span>%s</span>
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
                            <form method="POST" action="/changepin">
                                <input type="hidden" name="userId" value="%s">
                                <label>Current PIN:</label>
                                <input type="password" name="oldPin" required>
                                <label>New PIN:</label>
                                <input type="password" name="newPin" required>
                                <button type="submit">Update PIN</button>
                            </form>
                        </div>
                    </div>
                </body>
                </html>
                """,
                user.get("id"),
                user.get("name"),
                user.get("email"),
                user.get("number"),
                userId
            );
            
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            exchange.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            exchange.close();
        }
    }
    
    static class ChangePinHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("POST")) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseParams(body);
                
                int userId = Integer.parseInt(params.get("userId"));
                String oldPin = params.get("oldPin");
                String newPin = params.get("newPin");
                
                Map<String, Object> response = UserAuthentication.changePin(userId, oldPin, newPin);
                
                exchange.getResponseHeaders().set("Location", "/dashboard?msg=" + response.get("message"));
                exchange.sendResponseHeaders(302, 0);
                exchange.close();
            }
        }
    }
    
    static class LogoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("POST")) {
                UserAuthentication.logout();
                exchange.getResponseHeaders().set("Location", "/");
                exchange.sendResponseHeaders(302, 0);
                exchange.close();
            }
        }
    }
    
    static class APIRegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("POST")) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseParams(body);
                
                Map<String, Object> response = UserAuthentication.registration(
                    params.get("name"),
                    params.get("email"),
                    params.get("number"),
                    params.get("pin")
                );
                
                String redirect = (boolean) response.get("success") ? "/login" : "/register";
                exchange.getResponseHeaders().set("Location", redirect);
                exchange.sendResponseHeaders(302, 0);
                exchange.close();
            }
        }
    }
    
    static class APILoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equals("POST")) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseParams(body);
                
                Map<String, Object> response = UserAuthentication.login(
                    params.get("email"),
                    params.get("pin")
                );
                
                String redirect = (boolean) response.get("success") ? "/dashboard" : "/login";
                exchange.getResponseHeaders().set("Location", redirect);
                exchange.sendResponseHeaders(302, 0);
                exchange.close();
            }
        }
    }
    
    private static Map<String, String> parseParams(String body) {
        Map<String, String> params = new HashMap<>();
        if (body != null && !body.isEmpty()) {
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                    try {
                        params.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        params.put(kv[0], kv[1]);
                    }
                }
            }
        }
        return params;
    }
}
