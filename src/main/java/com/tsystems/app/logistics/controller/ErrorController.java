package com.tsystems.app.logistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ksenia on 30.11.2017.
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping
    public String catchError(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getAttribute("javax.servlet.error.status_code"));
        model.addAttribute("message", request.getAttribute("javax.servlet.error.message"));
        return "error";
    }
}
