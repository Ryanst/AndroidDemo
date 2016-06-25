package com.ryanst.app.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by zhengjuntong on 16/4/26.
 */
public class GlideActivity extends BaseSlideActivity {

    public static final int RATE = 2;
    ImageView ivGlide;
    Button btnLoadImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide);

        ivGlide = (ImageView) findViewById(R.id.iv_glide);

        btnLoadImage = (Button) findViewById(R.id.btn_load_image);

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://jcodecraeer.com/uploads/allimg/160316/1_1131246293.jpg";
                Uri uri = Uri.parse(url);
                loadImage(ivGlide, uri);
            }
        });
    }

    public class LoggingListener<T, R> implements RequestListener<T, R> {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            Log.d("GLIDE", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            Log.d("GLIDE", String.format(Locale.ROOT,
                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
            return false;
        }
    }

    void loadImage(ImageView imageView, Uri uri) {

        Glide
                .with(this)
                .using(new StreamUriLoader(this), InputStream.class)
                .from(Uri.class)
                .as(Options.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new BitmapSizeDecoder())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new LoggingListener<Uri, Options>())
                .load(uri)
                .into(new SimpleTarget<Options>() { // Target.SIZE_ORIGINAL is hidden in ctor
                    @Override
                    public void onResourceReady(Options resource, GlideAnimation glideAnimation) {
                        double realWidth = resource.outWidth;
                        double realHeight = resource.outHeight;
                        Point point = getImagePoint(realWidth, realHeight);

                        ViewGroup.LayoutParams layoutParams = ivGlide.getLayoutParams();
                        layoutParams.width = point.x;
                        layoutParams.height = point.y;
                        ivGlide.setLayoutParams(layoutParams);
                        Log.wtf("SIZE", String.format(Locale.ROOT, "%dx%d", resource.outWidth, resource.outHeight));
                    }
                });


        // normal load to display
        Glide
                .with(this)
                .load(uri)
                .placeholder(R.drawable.back)
                .error(R.drawable.beike)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new LoggingListener<Uri, GlideDrawable>())
                .into(imageView)
        ;
    }

    class BitmapSizeDecoder implements ResourceDecoder<File, Options> {
        @Override
        public Resource<Options> decode(File source, int width, int height) throws IOException {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(source.getAbsolutePath(), options);
            return new SimpleResource<>(options);
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    private Point getImagePoint(double realWidth, double realHeight) {
        Point point = screenDisplay(this);
        point.y = (int) (point.x * realHeight / realWidth);
        return point;
    }

    public Point screenDisplay(Context activity) {
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        Point point = new Point();
        point.x = metrics.widthPixels;
        point.y = metrics.heightPixels;
        return point;
    }

}
