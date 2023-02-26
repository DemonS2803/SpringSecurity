package ru.spmi.backend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/action")
public class ActionController {

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('permission:create')")
    public String createActionView() {
        System.out.println("hi from create");
        return "success";
    }

    @GetMapping("/write")
    @PreAuthorize("hasAuthority('permission:write')")
    public String writeActionView() {
        return "success";
    }

    @GetMapping("/read")
    @PreAuthorize("hasAuthority('permission:read')")
    public String readActionView() {
        return "success";
    }
}
