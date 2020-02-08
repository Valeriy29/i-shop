package com.example.ishop.rest;

import com.example.ishop.domain.UserEntity;
import com.example.ishop.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String addUser(UserEntity userEntity, Map<String, Object> model) {
        String page = registrationService.registrationUser(userEntity);
        if (page.equals("registration")) {
            model.put("message", "User exists!");
            return page;
        }
        return page;
    }

}
