package com.mycards.business;

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

public class API {

    private final String urlPath = "http://192.168.0.103:8080/carteira/api/";
    private final String erro = "Erro ao consumir API";
    private Model obj;
    private String url;

    public API(Model obj) {
        this.obj = obj;
        this.url = urlPath + this.obj.getClass().getSimpleName().toLowerCase();
    }

    public Model read() throws IOException, JSONException, IllegalAccessException {
        url += "/" + obj.getId();
        String content = doIt();
        JSONObject jsonObject = new JSONObject(content);
        return obj.toObject(jsonObject);
    }

    public List<Model> list() throws IOException, JSONException, IllegalAccessException, InstantiationException {
        String content = doIt();
        List<Model> models = new ArrayList<Model>();
        JSONArray jsonArray = new JSONArray(content);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Model model = obj.getClass().newInstance().toObject(jsonObject);
            models.add(model);
        }
        return models;
    }

    public void post() throws IOException, JSONException, IllegalAccessException {
        HttpPost httpPost = new HttpPost(url);
        doIt(httpPost);
    }

    public void put() throws IOException, JSONException, IllegalAccessException {
        HttpPut httpPut = new HttpPut(url);
        doIt(httpPut);
    }

    public void delete() throws IOException {
        String path = url + "/" + obj.getId();
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpDelete(path));
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            Log.d(erro, "");
        }
    }

    private HttpClient openHttpClient() {
        HttpClient client = new DefaultHttpClient();
        //HttpHost proxy = new HttpHost("10.199.38.61", 8080, "http");
        //client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return client;
    }

    private String doIt() throws IOException {
        HttpClient client = openHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        HttpResponse execute = client.execute(httpGet);

        InputStream content = execute.getEntity().getContent();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
        String s = "";
        StringBuilder builder = new StringBuilder();
        while ((s = buffer.readLine()) != null) {
            builder.append(s);
        }
        return builder.toString();
    }

    private void doIt(HttpEntityEnclosingRequestBase httpEntity) throws IOException, JSONException, IllegalAccessException {
        HttpClient client = openHttpClient();
        String json = obj.toJson().toString();
        StringEntity se = new StringEntity(json);
        httpEntity.setEntity(se);
        httpEntity.setHeader("Accept", "application/json");
        httpEntity.setHeader("Content-type", "application/json");
        HttpResponse httpResponse = client.execute(httpEntity);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            Log.d(erro, "");
        }
    }
}

