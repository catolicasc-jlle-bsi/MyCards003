package com.mycards.business;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public abstract class Model {
    public Long id;

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            for (Field field : this.getClass().getFields()) {
                if (isFieldMap(field.getType())) {
                    jsonObject.accumulate(field.getName(), field.get(this));
                }
            }
        } catch (Exception e) {
            Log.e("Erro ao converter objeto para json", e.getMessage());
        }
        return jsonObject;
    }

    public Model toObject(JSONObject json) {
        try {
            for (Field field : this.getClass().getFields()) {
                if (isFieldMap(field.getType())) {
                    if (field.getType().equals(Long.class)) {
                        field.set(this, json.getLong(field.getName()));
                    } else {
                        field.set(this, json.getString(field.getName()));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Erro ao converter json para objeto", e.getMessage());
        }
        return this;
    }

    private boolean isFieldMap(Type type) {
        return (type == String.class || type == Long.class);
    }
}