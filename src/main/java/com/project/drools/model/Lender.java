package com.project.drools.model;

import lombok.Data;

@Data
public class Lender {
    private Integer id;
    private Integer term;
    private Integer score;
    private String loanType;
    private Double rate;
}
