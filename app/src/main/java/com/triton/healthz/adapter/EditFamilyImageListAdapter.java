package com.triton.healthz.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.triton.healthz.R;
import com.triton.healthz.requestpojo.FamilyMembersEditRequest;

import java.util.List;

public class EditFamilyImageListAdapter extends RecyclerView.Adapter<EditFamilyImageListAdapter.AddImageListHolder> {
    private String TAG = "EditFamilyImageListAdapter";
    Context context;
    List<FamilyMembersEditRequest.PicBean> pet_img;
    View view;
    String fromactivity;

    public EditFamilyImageListAdapter(Context context, List<FamilyMembersEditRequest.PicBean> pet_img,String fromactivity) {
        this.context = context;
        this.pet_img = pet_img;
        this.fromactivity = fromactivity;


    }

    @NonNull
    @Override
    public AddImageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_images_upload, parent, false);

        return new AddImageListHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AddImageListHolder holder, final int position) {
        final FamilyMembersEditRequest.PicBean petImgBean = pet_img.get(position);

        Log.w(TAG,"ImagePic : "+petImgBean.getImage());

        if (petImgBean.getImage()!= null) {
            Glide.with(context)
                    .load(petImgBean.getImage())
                    .into(holder.certificate_pics_1);

        }
        if(fromactivity != null && fromactivity.equalsIgnoreCase("ViewFamilyMembersActivity")){
            holder.removeImg.setVisibility(View.INVISIBLE);
        }else{
            holder.removeImg.setVisibility(View.VISIBLE);
        }

        holder.removeImg.setOnClickListener(view -> {
            pet_img.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return pet_img.size();

    }

    public static class AddImageListHolder extends RecyclerView.ViewHolder {
        ImageView removeImg,certificate_pics_1;
        public AddImageListHolder(View itemView) {
            super(itemView);
            certificate_pics_1 = itemView.findViewById(R.id.certificate_pics_1);
            removeImg = itemView.findViewById(R.id.close);
        }
    }


}