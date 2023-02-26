package com.proj.controller;

import com.proj.form.UserCredentials;
import com.proj.form.validator.UserCredentialsRegisterValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.proj.domain.User;
import com.proj.service.JwtService;
import com.proj.service.UserService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserCredentialsRegisterValidator userCredentialsRegisterValidator;


    public UserController(JwtService jwtService, UserService userService, UserCredentialsRegisterValidator userCredentialsRegisterValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userCredentialsRegisterValidator = userCredentialsRegisterValidator;
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

//    @GetMapping("users/registration")
//    public User RegisterUserByJwt(@RequestParam String jwt) {
//        return jwtService.create(jwt);
//    }

    @GetMapping("users")
    public List<User> findUsers() {
        return userService.findAll();
    }

    @GetMapping("user/{id}")
    public User findUser(@PathVariable String id) {
        try {
            long uid = Long.parseLong(id);
            return userService.findById(uid);
        } catch (NumberFormatException ignored) {
            return userService.findByLogin(id);
        }
    }

    @GetMapping("tgUser/{tgId}")
    public User findTgUser(@PathVariable String tgId) {
        return userService.findByTgId(tgId);
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsRegisterValidator);
    }

    @PostMapping("register")
    public String register(@RequestBody @Valid UserCredentials userCredentials, BindingResult bindingResult) {
        User user = userService.register(userCredentials);
        return jwtService.create(user);
    }
}
