package me.s0wnd.userservice.controller;

import com.netflix.discovery.converters.Auto;
import me.s0wnd.userservice.dto.UserDto;
import me.s0wnd.userservice.service.UserService;
import me.s0wnd.userservice.vo.Greeting;
import me.s0wnd.userservice.vo.RequestUser;
import me.s0wnd.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/user-service/health_check")
    public String status() {
       return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

//    @GetMapping("/welcome_env")
//    public String welcome_env() {
//        return env.getProperty("greeting.message");
//    }

    @Autowired
    private Greeting greeting;

    @GetMapping("/user-service/welcome")
    public String welcome() {
        return greeting.getMessage();

    }

    @PostMapping("/user-service/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }
}
