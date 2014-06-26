package com.mycards.api;

import android.os.AsyncTask;

import com.mycards.business.Model;

public class Upload extends AsyncTask<Object, Void, Object> {

    private Model model;

    @Override
    protected Object doInBackground(Object... objects) {
        model = (Model) objects[0];
        if (isNovoRegistro()) {
            new API(model).post();
        } else {
            new API(model).put();
        }

        return null;
    }

    private boolean isNovoRegistro() {
        return model.id != null;
    }
}