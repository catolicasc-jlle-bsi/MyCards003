package com.mycards.api;

import android.util.Log;

import com.mycards.business.Model;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class API {

    private final String urlPath = "http://192.168.0.103:8080/carteira/api/";
    private Model obj;
    private String url;

    public API(Model obj) {
        this.obj = obj;
        this.url = urlPath + this.obj.getClass().getSimpleName().toLowerCase();
    }

    /* Não está sendo utilizado no momento e talvez nem seja utilizada
    public Model read() {
        Model model = null;
        try {
            url += "/" + obj.id;
            String content = executeAndReturnContent();
            JSONObject jsonObject = new JSONObject(content);
            model = obj.toObject(jsonObject);
        } catch (Exception e) {
            Log.e("Erro ao realizar leitura", e.getMessage());
        }
        return model;
    }
    */

    public List<Model> list() {
        List<Model> models = new ArrayList<Model>();
        try {
            String content = executeAndReturnContent();
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Model model = obj.getClass().newInstance().toObject(jsonObject);
                models.add(model);
            }
        } catch (Exception e) { }
        return models;
    }

    public void post() {
        HttpPost httpPost = new HttpPost(url);
        execute(httpPost);
    }

    public void put() {
        HttpPut httpPut = new HttpPut(url);
        execute(httpPut);
    }

    public void delete() {
        try {
            String path = url + "/" + obj.id;
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpDelete(path));
            //response.getStatusLine().getStatusCode(); // Se precisar saber o status de retorno: 200 está OK
        } catch (Exception e) { }
    }

    private HttpClient openHttpClient() {
        HttpClient client = new DefaultHttpClient();
        //HttpHost proxy = new HttpHost("10.199.38.61", 8080, "http");
        //client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return client;
    }

    private String executeAndReturnContent() {
        StringBuilder builder = new StringBuilder();
        try {
            HttpClient client = openHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            HttpResponse execute = client.execute(httpGet);

            InputStream content = execute.getEntity().getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                builder.append(s);
            }
        } catch (Exception e) { }
        return builder.toString();
    }

    private void execute(HttpEntityEnclosingRequestBase httpEntity) {
        try {
            HttpClient client = openHttpClient();
            String json = obj.toJson().toString();
            StringEntity se = new StringEntity(json);
            httpEntity.setEntity(se);
            httpEntity.setHeader("Accept", "application/json");
            httpEntity.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(httpEntity);
            //response.getStatusLine().getStatusCode(); // Se precisar saber o status de retorno: 200 está OK
        } catch (Exception e) { }
    }
}

