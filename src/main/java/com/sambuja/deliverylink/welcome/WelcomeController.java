package com.sambuja.deliverylink.welcome;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcome(){
        log.debug(">> welcome");
        return "list";
    }

    @GetMapping("/welcome")
    public String welcome2(){
        return "list";
    }
}
