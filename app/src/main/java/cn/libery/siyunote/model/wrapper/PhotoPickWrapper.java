package cn.libery.siyunote.model.wrapper;

import android.net.Uri;

import java.io.File;

import cn.libery.library_multiphotopick.photopick.ImageInfo;

public class PhotoPickWrapper {

    private ImageInfo imageInfo;
    private Uri uri;
    private String uriString;

    public PhotoPickWrapper(ImageInfo imageInfo, File file, String uriString) {
        this.imageInfo = imageInfo;
        this.uri = Uri.fromFile(file);
        this.uriString = uriString;
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public Uri getUri() {
        return uri;
    }

    public String getUriString() {
        return "file://"+uriString;
    }

}