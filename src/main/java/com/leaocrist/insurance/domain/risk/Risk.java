package com.leaocrist.insurance.domain.risk;

import jakarta.persistence.*;

@Entity
@Table(name = "risks")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String description;

    protected Risk() {
    }

    public Risk(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void updateInformation(String type, String description){
        this.type = type;
        this.description =description;
    }
}
