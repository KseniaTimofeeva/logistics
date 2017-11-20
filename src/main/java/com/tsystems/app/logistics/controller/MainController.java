package com.tsystems.app.logistics.controller;

import com.tsystems.app.logistics.service.api.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ksenia on 04.10.2017.
 */
@Controller
public class MainController {

    @RequestMapping({"/", "/login"})
    public String getLogin() {
        return "login";
    }

}
