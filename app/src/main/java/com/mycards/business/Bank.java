package com.mycards.business;

public class Bank extends Model {
    public String code;
    public String description;

    public Bank() {}
    public Bank(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return code + "\n" +
                description;
    }
}