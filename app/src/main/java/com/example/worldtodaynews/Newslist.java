package com.example.worldtodaynews;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Newslist extends AppCompatActivity {
private  static String JSON_URL= "https://newsapi.org/v2/top-headlines?country=us&apiKey=1d8e7d4ed0d54c0eb35c7eb523b6f091";


List<NewsModelClass> newslist;

RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslist);
newslist =new ArrayList<>();
recyclerView = findViewById(R.id.recyclerview);

GetData getData = new GetData();
getData.execute();


    }


    public class GetData extends AsyncTask<String,String,String>{


        private Object NewsModelClass;

        @Override
        protected String doInBackground(String... strings) {


            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {

                        current += (char) data;
                        data = isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }

                }
            } catch (Exception e){
                e.printStackTrace();

            }


                return current;
    }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject =new JSONObject(s);


                JSONArray jsonArray = jsonObject.getJSONArray("source");

for (int i=0;i<jsonArray.length(); i++){
    JSONObject jsonObject1 =jsonArray.getJSONObject(i);
    NewsModelClass model = new NewsModelClass();

model.setTitle(jsonObject1.getString("title"));
    model.setDescription(jsonObject1.getString("description"));
    model.setUrltoimage(jsonObject1.getString("urlToImage"));

newslist.add(model);
}

            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(newslist);

        }
    }

    private void PutDataIntoRecyclerView(List<NewsModelClass>newslist){
        NewsAdapter newsAdapter = new NewsAdapter(this, newslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(NewsAdapter);


    }
}