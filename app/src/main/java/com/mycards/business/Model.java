package com.mycards.business;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;

public abstract class Model {
    abstract Long getId();

    public JSONObject toJson() throws JSONException, IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        for (Field field : this.getClass().getFields()) {
            if (field.getType() == String.class || field.getType() == Long.class) {
                jsonObject.accumulate(field.getName(), field.get(this));
            }
        }
        return jsonObject;
    }

    public Model toObject(JSONObject json) throws JSONException, IllegalAccessException {
        for (Field field : this.getClass().getFields()) {
            //TODO: melhorar essa condicional, sem precisar especificar o nome da classe e sim todas as classes que herdam de Model
            //Não set dados de objetos, isso é feito no método override da classe.
            if (!json.get(field.getName()).equals(null) && !field.getName().equals("bank") && !field.getName().equals("flag")) {
                if (field.getType().equals(Long.class)) {
                    field.set(this, json.getLong(field.getName()));
                } else {
                    field.set(this, json.get(field.getName()));
                }
            }
        }
        return this;
    }
}