package com.zioneer.robotqcsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 根路径重定向到 Swagger，避免直接访问 localhost:8080 出现 403
 */
@Controller
public class RootController {

    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }
}
