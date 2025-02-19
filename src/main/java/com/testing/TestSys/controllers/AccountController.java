package com.testing.TestSys.controllers;

import com.testing.TestSys.models.AppUser;
import com.testing.TestSys.models.RegisterDto;
import com.testing.TestSys.repositories.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    private AppUserRepository repo;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result) {

        if (!registerDto.getPassword().equals(registerDto.getConfirmationPassword())){
            result.addError(
                    new FieldError("registerDto","confirmationPassword","Password and Confirmation Password do not match")
            );
        }

        AppUser appUser = repo.findByUsername(registerDto.getUsername());
        if (appUser != null) {
            result.addError(
                    new FieldError("registerDto","username","Username is already used")
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {

            var bCryptEncoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setUsername(registerDto.getUsername());
            newUser.setRole("student");
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

            repo.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);
        }
        catch (Exception ex) {
            result.addError(
                    new FieldError("registerDto","username",ex.getMessage())
            );
        }

        return "register";
    }
}
