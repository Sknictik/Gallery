package com.example.Gallery;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public abstract class ImageUrlJSONParser {

    public static List<String> parse(InputStream is) throws IOException {
        List<String> imgUrls = new ArrayList<String>();
        String input = convertStreamToString(is);
        if (input == null)
            return null;
        try {
            JSONArray imagesArr = new JSONArray(input);
            String url;
                for (int i = 0; i < imagesArr.length(); i++) {
                    url = imagesArr.getString(i);
                    imgUrls.add(url);
                }
        } catch (JSONException e) {
            e.printStackTrace();
            imgUrls = null;
        }
        return imgUrls;
    }


    private static String convertStreamToString(InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
