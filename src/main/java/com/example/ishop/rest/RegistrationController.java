package com.example.ishop.rest;

import com.example.ishop.domain.UserEntity;
import com.example.ishop.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid UserEntity userEntity, BindingResult bindingResult, Model model) {
//        if (userEntity.getPassword() != null && !userEntity.getPassword().equals(userEntity.getPassword2())) {
//            model.addAttribute("passwordError", "Passwords are different!");
//            return "registration";
//        }
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
//
//            model.mergeAttributes(errors);
//            return "registration";
//        }
        String page = registrationService.registrationUser(userEntity);
        if (page.equals("registration")) {
            model.addAttribute("usernameError", "User exists!");
            return page;
        }
        return page;
    }

}
