package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.Authenticated;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticatedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticatedController {

    private AuthenticatedService authenticatedService;

    public AuthenticatedController(AuthenticatedService authenticatedService) {
        this.authenticatedService = authenticatedService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignUp(Model model){
        model.addAttribute("authenticated", new Authenticated());
        model.addAttribute("isRegister", "0");
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postSignUp(@ModelAttribute Authenticated registerInformation, Model model){
        boolean isRegister = authenticatedService.registerNewAccount(registerInformation);
        model.addAttribute("isRegister", isRegister);
        model.addAttribute("authenticated", registerInformation);
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logIn(Model model){
        model.addAttribute("isLoginSuccess", "0");
        return "login";
    }
}
