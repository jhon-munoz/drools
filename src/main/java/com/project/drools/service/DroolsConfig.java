package com.project.drools.service;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Configuration
public class DroolsConfig {

    private static final String G = "com.example";
    private static final String A = "demo";
    private static final String V = "1.0.0";

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieServices kieServices = KieServices.Factory.get();

        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        // Add rule files to KieFileSystem
        Resource[] ruleFiles = new PathMatchingResourcePatternResolver().getResources("classpath:/rules/*.drl");
        for (Resource file : ruleFiles) {
            String content = new String(file.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            kieFileSystem.write("src/main/resources/" + file.getFilename(), content);
//            kieFileSystem.write("src/main/resources/" + file.getFilename(), file.getInputStream());
        }
//        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/discount-rules.drl"));

        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem).buildAll();
        KieModule kieModule = kb.getKieModule();
//        ReleaseId releaseId = kieServices.newReleaseId(G,A,V);


        return kieServices.newKieContainer(kieModule.getReleaseId());
//        return kieServices.newKieContainer(releaseId);
    }

    @Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }

    @Bean
    public KieScanner kieScanner(KieContainer kieContainer) {
        KieScanner kieScanner = KieServices.Factory.get().newKieScanner(kieContainer);
        kieScanner.start(1L); // Adjust the scan interval as needed
        return kieScanner;
    }
}
