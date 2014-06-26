package com.mycards.business;

import org.json.JSONException;
import org.json.JSONObject;

public class Card extends Model {
    public Long id;
    public String name;
    public String cardName;
    public String cardNumber;
    public String dateValidatedMounth;
    public String dateValidatedYear;
    public String verifyCode;
    public Flag flag;
    public Bank bank;
    //public User user;

    public Card() {}
    public Card(Long id) { this.id = id; }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public JSONObject toJson() throws JSONException, IllegalAccessException {
        JSONObject jsonObject = super.toJson();

        jsonObject.accumulate("bank", bank.toJson());
        jsonObject.accumulate("flag", flag.toJson());

        return jsonObject;
    }

    @Override
    public String toString() {
        return cardName;
    }
}