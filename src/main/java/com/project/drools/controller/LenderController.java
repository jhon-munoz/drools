package com.project.drools.controller;

import com.project.drools.model.Lender;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LenderController {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private KieScanner kieScanner;

    @PostMapping("/blue")
    public Lender applyDiscount(@RequestBody Lender lender) {
        kieSession.insert(lender);
        kieSession.fireAllRules();
        return lender;
    }
}
