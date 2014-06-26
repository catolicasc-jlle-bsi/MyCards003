package com.mycards.business;

public class Flag extends Model {

    public Long id;
    public String description;

    public Flag() {}
    public Flag(Long id) {
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
