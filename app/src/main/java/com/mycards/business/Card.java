package com.mycards.business;

import org.json.JSONException;
import org.json.JSONObject;

public class Card extends Model {
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
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        try {
            if (bank != null) {
                jsonObject.accumulate("bank", bank.toJson());
            }
            if (flag != null) {
                jsonObject.accumulate("flag", flag.toJson());
            }
        } catch (Exception e) {}
        return jsonObject;
    }

    @Override
    public String toString() {
        return name + "\n" +
                cardName + "\n" +
                cardNumber + "\n" +
                dateValidatedMounth + "/" + dateValidatedYear;
    }
}