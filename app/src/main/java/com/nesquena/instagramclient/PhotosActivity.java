package com.nesquena.instagramclient;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;
//import java.lang.Throwable;
//import java.lang.String;



public class PhotosActivity extends ActionBarActivity {
    public static final String CLIENT_ID="765936c0ec694b3e857abefed385c88b";
    //4ff912bb27cc492c8b35785bdc59f661
    private ArrayList<InstagramPhoto> photos;
    private com.nesquena.instagramclient.InstagramPhotosAdapter aPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // SEND OUT API REQUEST to POPULAR PHOTOS
        photos = new ArrayList<>();
        //1. Create the adapter limking it to the source
        aPhotos = new com.nesquena.instagramclient.InstagramPhotosAdapter(this,photos);
        //2. Find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //3. Set the adapter binding it the listview
        lvPhotos.setAdapter(aPhotos);
        //fetch the popular photos
        fetchPopularPhotos();
    }

    //Trigger API request
    public void fetchPopularPhotos(){
        //Client ID: 5e4bb8b44e2cad975512543ecdb8
        //-Popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
         //-Response


        String url="https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //Create the netwook client
        AsyncHttpClient client=new AsyncHttpClient();
       //Triger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
        //client.get(url, null, new JsonHttpresponseHandler(){
            //onSuccess (worked, 200)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            //public void onSuccess(int statusCode, Header[] headers, JSONObject response){
           //Expecting aJSON object



           // Iterate each of the photo items and decode the item into a java throwable
             JSONArray photosJSON = null;
             try {
                photosJSON = response.getJSONArray("data"); //array of posts
                 // iterate array of posts
                 for (int i = 0; i < photosJSON.length(); i++){
                 //get the json object at the position
                 JSONObject photoJSON = photosJSON.getJSONObject(i);
                 //decode the attributes of tje json into a the data model
                 InstagramPhoto photo = new InstagramPhoto();
                 //Author Name:{"data"=>[x]=>"user"=>"username"}
                 photo.username = photoJSON.getJSONObject("user").getString("username");
                 // Caption:{"data"=>[x]=>"caption"=>"text"}
                 photo.caption = photoJSON.getJSONObject("caption").getString("text");
                 //Type:{"data"=>[x]=>type} ("image" or "video")
                 // photo.type = photoJSON.getJSONObject("caption").getString("text");
                 // URL:{"data"=>[x]=>"image"=>"standard_resolution"=>"url"}
                 photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_revolution").getString("url");
                 //Height
                 photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_revolution").getInt("height");
                 // Likes Count
                 photo.LikesCount = photoJSON.getJSONObject("likes").getInt("count");
                 // Add decoded object to the photo
                 photos.add(photo);
                 }
             }catch (JSONException e){
                 e.printStackTrace();
             }
        //Log.i("DEBUG", response.toString());
             // calback
                aPhotos.notifyDataSetChanged();

    }
        //onfailure
      /*  @Override
        public  void onFailure(int statusCode, Header[] headers, Throwable throwable){
            super.onFailure();
            //DO SOMETHING

        }*/
        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
