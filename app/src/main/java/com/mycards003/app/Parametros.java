package com.mycards003.app;

import android.content.Intent;

import com.mycards.business.Model;

/**
 * Created by Takeshi on 25/06/2014.
 */
public class Parametros {

    private static Parametros parametros = null;
    public static Model model;

    private Parametros() { }

    public static Parametros getInstance() {
        if (parametros == null) {
            parametros = new Parametros();
        }
        return parametros;
    }
}
