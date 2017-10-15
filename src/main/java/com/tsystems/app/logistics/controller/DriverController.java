package com.tsystems.app.logistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ksenia on 08.10.2017.
 */
@Controller
@RequestMapping("/driver")
public class DriverController {

    @RequestMapping
    public String getDriver() {
        return "page";
    }


}
