package com.gcash.controller;

import com.gcash.service.UserAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class AuthController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String number,
            @RequestParam String pin,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Map<String, Object> response = UserAuthentication.registration(name, email, number, pin);
        
        if ((boolean) response.get("success")) {
            redirectAttributes.addFlashAttribute("message", response.get("message"));
            redirectAttributes.addFlashAttribute("userId", response.get("userId"));
            return "redirect:/login";
        } else {
            model.addAttribute("error", response.get("message"));
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("number", number);
            return "register";
        }
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String pin,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Map<String, Object> response = UserAuthentication.login(email, pin);
        
        if ((boolean) response.get("success")) {
            redirectAttributes.addFlashAttribute("userId", response.get("userId"));
            redirectAttributes.addFlashAttribute("userName", response.get("userName"));
            redirectAttributes.addFlashAttribute("message", "Welcome " + response.get("userName") + "!");
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", response.get("message"));
            model.addAttribute("email", email);
            return "login";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Integer userId = UserAuthentication.getCurrentUserId();
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Map<String, Object> userInfo = UserAuthentication.getUserInfo(userId);
        model.addAttribute("user", userInfo);
        return "dashboard";
    }
    
    @PostMapping("/changepin")
    public String changePin(
            @RequestParam int userId,
            @RequestParam(name = "oldPin") String oldPin,
            @RequestParam(name = "newPin") String newPin,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Map<String, Object> response = UserAuthentication.changePin(userId, oldPin, newPin);
        
        if ((boolean) response.get("success")) {
            redirectAttributes.addFlashAttribute("success", response.get("message"));
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", response.get("message"));
            Map<String, Object> userInfo = UserAuthentication.getUserInfo(userId);
            model.addAttribute("user", userInfo);
            return "dashboard";
        }
    }
    
    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        UserAuthentication.logout();
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/";
    }
}
