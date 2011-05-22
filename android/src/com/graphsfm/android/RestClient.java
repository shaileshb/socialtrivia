package com.graphsfm.android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 //earbuzilla

public class RestClient {
 
    private static String convertStreamToString(InputStream is) throws Exception {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
 
    public static ArrayList<MediaClip> connect(String url) throws Exception
    {
 
        HttpClient httpclient = new DefaultHttpClient();
 
        // Prepare a request object
        HttpGet httpget = new HttpGet(url); 
 
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("earbuzilla",response.getStatusLine().toString());
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
 
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("earbuzilla",result);
 
                // A Simple JSONObject Creation
                JSONObject json=new JSONObject(result);
 
                // Closing the input stream will trigger connection release
                instream.close();
                JSONArray jsonArray = json.getJSONArray("mediaclip");
                ArrayList<MediaClip> mediaclips = new ArrayList<MediaClip>();
                
                for (int i=0; i<jsonArray.length(); i++) {
                    MediaClip mediaClip = new MediaClip();
                    mediaClip.setLocation(jsonArray.getJSONObject(i).getString("location"));
                    mediaClip.setBandName(jsonArray.getJSONObject(i).getString("band"));
                    mediaClip.setSongName(jsonArray.getJSONObject(i).getString("song"));
                	mediaclips.add(mediaClip);
                }
                return mediaclips;
    			
            }
 
 
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        throw new Exception();
    }
 
}
