package com.sambuja.deliverylink.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcome(){
        return "list";
    }

    @GetMapping("/welcome")
    public String welcome2(){
        return "list";
    }
}
