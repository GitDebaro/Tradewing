package com.tradewing.controllers.impl;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendControllerImpl implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Cuando Spring Boot detecte un error (404, etc.), redirigimos al frontend
        return "forward:/index.html";
    }
}