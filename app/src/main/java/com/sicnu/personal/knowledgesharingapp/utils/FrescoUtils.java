package com.sicnu.personal.knowledgesharingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class FrescoUtils {
    public static DraweeController getDefaultImageRequest(String uriPath){
        Uri uri = Uri.parse(uriPath);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(PxUtils.getScreentWidth(),PxUtils.dp2px(180)))
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setImageRequest(request)
                .build();

        return mDraweeController;
    }
    public static ImagePipelineConfig getDefaultImagePipelineConfig(Context context){
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryName("KnowLedgeIamgeCache")

                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        return config;
    }
}
