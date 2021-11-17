package com.triton.healthZ.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.triton.healthZ.R;
import com.triton.healthZ.api.APIClient;
import com.triton.healthZ.responsepojo.DoctorDetailsResponse;

import java.util.List;


public class ViewPagerClinicDetailsAdapter extends PagerAdapter {
    private DoctorDetailsResponse.DataBean.ClinicPicBean currentItem;
    List<DoctorDetailsResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList;
    private Context context;
    private LayoutInflater inflater;
    private View itemView;

    private String TAG = "ViewPagerClinicDetailsAdapter";

    public ViewPagerClinicDetailsAdapter(Context context, List<DoctorDetailsResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList){

        this.context = context;
        this.doctorclinicdetailsResponseList = doctorclinicdetailsResponseList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return doctorclinicdetailsResponseList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View itemView = inflater.inflate(R.layout.sliding_image, view, false);
        ImageView imageView = itemView.findViewById(R.id.itemImage);


        try {
            String imageURL = doctorclinicdetailsResponseList.get(position).getClinic_pic();

            if(imageURL != null && !imageURL.isEmpty()){

                Log.w(TAG, "imageURL : "+imageURL);

                Glide.with(context)
                        .load(imageURL)
                        .into(imageView);
            }else{
                Glide.with(context)
                        .load(APIClient.BANNER_IMAGE_URL)
                        .into(imageView);

            }


        } catch (Exception e) {
            // Handle the condition when str is not a number.
        }






        view.addView(itemView);

        return itemView;

    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
