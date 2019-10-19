package com.teamkaizen.kisha.api;

import com.teamkaizen.kisha.user.User;
import com.teamkaizen.kisha.user.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@RestController
@RequestMapping("/api/user")

public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    //TODO For testing/demo purposes only.
    //TODO Revise web security by implementing OAuth2 with JWT Authentication. If will go for full implementation

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

}
