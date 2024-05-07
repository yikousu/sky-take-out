package com.sky.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/test")
public class TestController {
    @GetMapping()
    public String test(){
        return "yes";
    }


}
