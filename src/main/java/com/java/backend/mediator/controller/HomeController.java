
package com.java.backend.mediator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.backend.mediator.ButtonManagedBean;
import com.java.backend.mediator.SpringContext;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
    	ButtonManagedBean buttonManagedBean = SpringContext.getBean(ButtonManagedBean.class);
    	return buttonManagedBean.toSignUp();
        //return "Hello from Mediator App Service (in the staging slot)!";
    }
}