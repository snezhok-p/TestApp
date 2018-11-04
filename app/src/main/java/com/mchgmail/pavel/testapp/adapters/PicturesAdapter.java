package com.mchgmail.pavel.testapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mchgmail.pavel.testapp.R;
import com.mchgmail.pavel.testapp.viewHolders.PictureViewHolder;
import com.mchgmail.pavel.testapp.helper.Ajax;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PicturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int limit = 10;

    private final String type;
    private final String text;
    private final Activity activity;
    private final LayoutInflater inflater;
    private ArrayList<String> images;

    public PicturesAdapter(Activity activity, String type, String text){
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.images = new ArrayList<>();
        this.type = type;
        this.text = text;
        getImages();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PictureViewHolder(inflater.inflate(R.layout.layout_pictures, viewGroup,false),activity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PictureViewHolder pictureViewHolder = (PictureViewHolder) viewHolder;
        String[] links = {images.get(i*3).replaceAll("\\\\", ""),
                images.get(i*3+1).replaceAll("\\\\", ""),
                images.get(i*3+2).replaceAll("\\\\", "")};
        pictureViewHolder.showImages(links);

        Log.d("NUMBER_IMAGE", i+"/"+images.size()/3);
        if (i >= (images.size()/3 - 3)) {
            Log.d("NUMBER_IMAGE", "RELOAD");
            getImages();
        }
    }

    @Override
    public int getItemCount() {
        if (images == null || images.size() == 0) {
            return 0;
        }
        return images.size()/3;
    }

    public void reload(ArrayList<String> __images) {
        images.addAll(__images);
        notifyItemInserted(this.images.size() - 1);
    }

    public void reset() {
        images = new ArrayList<>();
        notifyDataSetChanged();
    }


    public void getImages() {

        new Ajax(new Ajax.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                ArrayList<String> images = new ArrayList<>();
                try {
                    Log.d("#################", "########################################");
                    if (output != null) {
                        Log.d("IMG_LINK", output);


                        JSONObject fieldJson = new JSONObject(output);
                        JSONArray jsonArray = new JSONArray(fieldJson.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i += 3) {
                            images.add(getUrlFromJson(jsonArray.getJSONObject(i)));
                            images.add(getUrlFromJson(jsonArray.getJSONObject(i + 1)));
                            images.add(getUrlFromJson(jsonArray.getJSONObject(i + 2)));

                        }
                        reload(images);
                    } else {
                        showAlert(output);
                    }
                } catch (JSONException e) {
                    showAlert("ERROR: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, type, text, limit, images.size()).execute();
    }

    private String getUrlFromJson(JSONObject jsonObject) {
        JSONObject image = null;
        try {
            image = new JSONObject(jsonObject.getString("images"));
            JSONObject original_still = new JSONObject(image.getString("fixed_width_downsampled"));
//            JSONObject original_still = new JSONObject(image.getString("original"));
            String url = original_still.getString("url");
            return url;
        } catch (JSONException e) {
            return "";
        }
    }

    private void showAlert(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();*/
    }
}
