package me.s0wnd.userservice.controller;

import com.netflix.discovery.converters.Auto;
import me.s0wnd.userservice.dto.UserDto;
import me.s0wnd.userservice.jpa.UserEntity;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status() {
       return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

//    @GetMapping("/welcome_env")
//    public String welcome_env() {
//        return env.getProperty("greeting.message");
//    }

    @Autowired
    private Greeting greeting;

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();

    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto user = userService.getUserByUserId(userId);

        ResponseUser returnUser = new ModelMapper().map(user, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnUser);

    }
}
