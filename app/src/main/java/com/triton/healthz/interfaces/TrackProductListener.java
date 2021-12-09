package com.triton.healthz.interfaces;

import com.triton.healthz.responsepojo.PetLoverVendorOrderDetailsResponse;

import java.util.List;

public interface TrackProductListener {
    void trackProductListener(String fromactivity, int product_id, String orderid, String TAG, List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> product_details);
}
