package me.s0wnd.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
@Slf4j
public class SecondServiceController {
    Environment env;

    @Autowired
    public SecondServiceController(Environment env) {
       this.env = env;
    }

   @GetMapping("/welcome")
    public String welcome() {
       return "Welcome to the 2nd service";
   }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info("---->" + header);
        return "Hello in 2nd Service";
    }

    @GetMapping("/check")
    public String check() {
        return String.format("Hi! This is 2nd Service on PORT %s", env.getProperty("local.server.port"));
    }
}
