package me.s0wnd.userservice.controller;

import com.netflix.discovery.converters.Auto;
import me.s0wnd.userservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;

    @Autowired
    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
       return "It's Working in User Service";
    }

    @GetMapping("/welcome_env")
    public String welcome_env() {
        return env.getProperty("greeting.message");
    }

    @Autowired
    private Greeting greeting;

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();

    }
}
