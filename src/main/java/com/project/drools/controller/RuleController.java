package com.project.drools.controller;

import com.project.drools.model.Product;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RuleController {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private KieScanner kieScanner;

    @PostMapping("/applyDiscount")
    public Product applyDiscount(@RequestBody Product product) {
        kieSession.insert(product);
        kieSession.fireAllRules();
        return product;
    }

    @PostMapping("/reload")
    public String reloadRules() {
        kieScanner.stop();
        kieScanner.scanNow();
        kieScanner.start(1L);
        return "Reglas recargadas exitosamente.";
    }
}
