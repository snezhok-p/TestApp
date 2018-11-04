package com.mchgmail.pavel.testapp.viewHolders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mchgmail.pavel.testapp.FullImageActivity;
import com.mchgmail.pavel.testapp.MainActivity;
import com.mchgmail.pavel.testapp.R;

public class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Activity activity;

    public PictureViewHolder(@NonNull View itemView, Activity activity) {
        super(itemView);
        this.activity = activity;
    }

    public void showImages(String[] link) {

        setImage(R.id.drawee_view0, link[0]);
        setImage(R.id.drawee_view1, link[1]);
        setImage(R.id.drawee_view2, link[2]);

    }

    private void setImage(int id , final String link) {
        Uri uri = Uri.parse(link);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        SimpleDraweeView gif = itemView.findViewById(id);
        gif.setController(controller);
        gif.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGif(link);
            }
        });
    }

    public void showGif(String link) {
//        link = link.replace("-preview", "");
//        link = link.replace("giphy-preview", "giphy-downsized");
//        link = link.replace("giphy-preview", "giphy");
//        link = link.replace("giphy-preview", "giphy");
        link = link.replace("200w_d", "giphy");
//        link = link.replace("giphy-preview", "giphy-downsized_s");
        Log.d("IMAGE-FULL", link);

        Intent loginIntent = new Intent(activity, FullImageActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        loginIntent.putExtra("link", link);
        ((MainActivity)activity).startActivity(loginIntent);
        ((MainActivity)activity).overridePendingTransition( 0, 0 );
    }
    @Override
    public void onClick(View v) {

    }
}
