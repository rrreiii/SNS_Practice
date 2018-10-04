package com.example.hirasawarei.sns_beta.myPage;

import android.content.Context;
import android.widget.ImageView;

import com.example.hirasawarei.sns_beta.R;
import com.squareup.picasso.Picasso;

/**
 * Created by hirasawarei on 18/08/17.
 */

public class PicassoClient {

    public static void downloadImage(Context context, String imageUrl, ImageView img)
    {
        if(imageUrl!=null && imageUrl.length()>0)
        {
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(img);
        }else {
            Picasso.with(context).load(R.drawable.placeholder).into(img);
        }
    }


}
