package cn.libery.library_multiphotopick.photopick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;


import cn.libery.library_multiphotopick.R;


public class ImagePagerFragment extends Fragment {

    private String uri;

    public ImagePagerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uri = getArguments().getString("uri");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_pager, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image_pager);
        ImageLoader.getInstance().displayImage(uri, image);
        return view;
    }


}
