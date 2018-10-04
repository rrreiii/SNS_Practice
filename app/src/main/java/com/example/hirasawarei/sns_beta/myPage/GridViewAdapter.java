package com.example.hirasawarei.sns_beta.myPage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hirasawarei.sns_beta.Post;
import com.example.hirasawarei.sns_beta.R;

import java.util.ArrayList;

/**
 * Created by hirasawarei on 18/08/17.
 */

public class GridViewAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<Post> posts;
    LayoutInflater inflater;


    public GridViewAdapter(Context ctx, ArrayList<Post> posts){
        this.ctx = ctx;
        this.posts = posts;

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.single_image_for_gridview,parent,false);
            holder = new ViewHolder();
            holder.photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
            convertView.setTag(holder);

//            imageView.setLayoutParams(new GridView.LayoutParams(200,200));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(2,2,2,2);


        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Post post = posts.get(position);
        PicassoClient.downloadImage(ctx,post.getImageURL(),holder.photoImageView);
        return convertView;
    }

    static class ViewHolder{
        ImageView photoImageView;

    }

}
