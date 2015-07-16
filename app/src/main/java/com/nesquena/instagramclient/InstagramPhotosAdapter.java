package com.nesquena.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nesquena.instagramclient.InstagramPhoto;
import com.nesquena.instagramclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
   //what data do we need from the activity
   //context, data source
   public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects){
       super(context, android.R.layout.simple_list_item_1, objects);
   }
    // What our looks like
    // Use the template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null){
            // Create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // Lookup the views for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        // Insert the item data into each of the view  items
        tvCaption.setText(photo.caption);
        // Clear out the imageview if it was recycled (fight away)
        ivPhoto.setImageResource(0);
        // Insert the image picasso (send out async)
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        //Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        // Return the createed item as a view
        return convertView;
    }
}
