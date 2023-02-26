package  com.proj.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.proj.domain.User;
import  com.proj.exception.ValidationException;
import  com.proj.form.UserCredentials;
import  com.proj.form.validator.UserCredentialsEnterValidator;
import  com.proj.service.JwtService;
import com.proj.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1")
public class JwtController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserCredentialsEnterValidator userCredentialsEnterValidator;

    public JwtController(JwtService jwtService, UserService userService, UserCredentialsEnterValidator userCredentialsEnterValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userCredentialsEnterValidator = userCredentialsEnterValidator;
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsEnterValidator);
    }

    @PostMapping("jwt")
    public String create(@RequestBody @Valid UserCredentials userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = userService.findByLoginAndPassword(userCredentials.getLogin(), userCredentials.getPassword());
        return jwtService.create(user);
    }
}
