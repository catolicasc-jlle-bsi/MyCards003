package com.mycards.business;

public class Bank extends Model {
    public Long id;
    public String code;
    public String description;

    public Bank() {}
    public Bank(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return description;
    }
}