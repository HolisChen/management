package com.mg;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('INDEX')")
    public String index() {
        return "INDEX";
    }
}
