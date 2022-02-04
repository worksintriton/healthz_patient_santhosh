package com.triton.healthz.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.healthz.R;
import com.triton.healthz.activity.NotificationActivity;
import com.triton.healthz.adapter.ProductDetailsAdapter;
import com.triton.healthz.api.APIClient;
import com.triton.healthz.api.RestApiInterface;
import com.triton.healthz.interfaces.TrackProductListener;
import com.triton.healthz.requestpojo.TrackOrderDetailsRequest;
import com.triton.healthz.responsepojo.PetLoverVendorOrderDetailsResponse;
import com.triton.healthz.responsepojo.TrackOrderDetailsResponse;
import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackOrderActivity extends AppCompatActivity implements View.OnClickListener, TrackProductListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TrackOrderActivity" ;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_date)
    TextView txt_order_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_booking_id)
    TextView txt_booking_id;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_payment_method)
    TextView txt_payment_method;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_total_order_cost)
    TextView txt_total_order_cost;




    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_booked)
    ImageView img_vendor_booked;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_booked_date)
    TextView txt_booked_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_confirmed)
    ImageView img_vendor_confirmed;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_confirm_date)
    TextView txt_order_confirm_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_order_confirm)
    TextView txt_edit_order_confirm;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_reject_date)
    TextView txt_order_reject_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_vendor_reject_date_reason)
    TextView txt_order_vendor_reject_date_reason;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_rejected)
    ImageView img_vendor_order_rejected;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_order_reject)
    TextView txt_edit_order_reject;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_order_reject_bypetlover)
    LinearLayout ll_order_reject_bypetlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_reject_date_petlover)
    TextView txt_order_reject_date_petlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_reject_date_reason)
    TextView txt_order_reject_date_reason;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_rejected_bypetlover)
    ImageView img_vendor_order_rejected_bypetlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_order_reject_petlover)
    TextView txt_edit_order_reject_petlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_dispatched)
    ImageView img_vendor_order_dispatched;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_dispatch_date)
    TextView txt_order_dispatch_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_dispatch_packdetails)
    TextView txt_order_dispatch_packdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_transit)
    ImageView img_vendor_order_transit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_transit_date)
    TextView txt_order_transit_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_order_reject)
    LinearLayout ll_order_reject;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;


    private int _id;
    private String orderid;
    private String fromactivity,payment_method;
    private List<TrackOrderDetailsResponse.DataBean.ProdcutTrackDetailsBean> prodcutTrackDetailsBeanList;

    List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> product_details;

   /**/

      @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_status)
    TextView txt_order_status;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_street)
    TextView txt_shipping_address_street;

/*    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_name)
    TextView txt_shipping_address_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_city)
    TextView txt_shipping_address_city;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_state_pincode)
    TextView txt_shipping_address_state_pincode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_phone)
    TextView txt_shipping_address_phone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_landmark)
    TextView txt_shipping_address_landmark;*/

   /* @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_order_status)
    ImageView img_order_status;*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_expand_arrow)
    ImageView img_expand_arrow;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_orderdetails)
    LinearLayout ll_orderdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_expand_arrow_shipping)
    ImageView img_expand_arrow_shipping;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_shippingaddress)
    LinearLayout ll_shippingaddress;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_productdetails)
    RecyclerView rv_productdetails;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_expand_arrow_productdetails)
    ImageView img_expand_arrow_productdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_productdetails)
    LinearLayout ll_productdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_products)
    TextView txt_no_products;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_of_items)
    TextView txt_no_of_items;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_cancell_order)
    TextView txt_cancell_order;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.scrollablContent)
    ScrollView scrollablContent;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_of_prodslabel)
    TextView txt_no_of_prodslabel;

    private  boolean button1IsVisible = false;
    private  boolean ShippingIsVisible = false;
    private  boolean productsIsVisible = false;

    private List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> productdetailslist;
    private List<Integer> product_id = new ArrayList<>();

    /* Petlover Bottom Navigation */

    BottomNavigationView bottom_navigation_view;
    FloatingActionButton floatingActionButton;

    public static String active_tag = "4";


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order_status);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getInt("_id");
            orderid = extras.getString("orderid");
            fromactivity = extras.getString("fromactivity");
            product_details = (List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean>) extras.getSerializable("product_details");
           payment_method = extras.getString("payment_method");
            Log.w(TAG,"_id : "+_id);
        }
        if(product_details!=null&&product_details.size()>0){

            setView(product_details);
        }
        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.track_order));
        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerProfileScreenActivity.class);
                intent.putExtra("fromactivity",TAG);
                intent.putExtra("_id",_id);
                intent.putExtra("orderid",orderid);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(this);

        txt_booked_date.setText(" ");
        txt_order_confirm_date.setText("");
        txt_order_dispatch_packdetails.setText("");
        txt_order_reject_date_reason.setText("");
        txt_order_dispatch_date.setText(" ");
        txt_order_transit_date.setText(" ");




        if (new ConnectionDetector(TrackOrderActivity.this).isNetworkAvailable(TrackOrderActivity.this)) {
            petlover_fetch_single_product_detail_ResponseCall();
        }


        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottomNavigation);
        floatingActionButton = include_petlover_footer.findViewById(R.id.fab);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);

        floatingActionButton.setImageResource(R.drawable.ic_hzhome_png);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDirections("1");
            }
        });



    }

    private void setView(List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> product_details) {
        rv_productdetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_productdetails.setItemAnimator(new DefaultItemAnimator());
        ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter(getApplicationContext(),product_details,orderid,fromactivity,this, false);
        rv_productdetails.setAdapter(productDetailsAdapter);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.shop:
                callDirections("2");
                break;
            case R.id.services:
                callDirections("3");
                break;
            case R.id.care:
                callDirections("4");
                break;
            case R.id.community:
                callDirections("5");
                break;

            default:
                return  false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.rl_homes:
                callDirections("1");
                break;
            case R.id.rl_home:
                callDirections("1");
                break;
            case R.id.rl_shop:
                callDirections("2");
                break;
            case R.id.rl_service:
                callDirections("3");
                break;
            case R.id.rl_care:
                callDirections("4");
                break;
            case R.id.rl_comn:
                callDirections("5");
                break;
        }
    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void petlover_fetch_single_product_detail_ResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<TrackOrderDetailsResponse> call = apiInterface.petlover_fetch_single_product_detail_ResponseCall(RestUtils.getContentType(), trackOrderDetailsRequest());
        Log.w(TAG,"TrackOrderDetailsResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<TrackOrderDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<TrackOrderDetailsResponse> call, @NonNull Response<TrackOrderDetailsResponse> response) {

                Log.w(TAG,"TrackOrderDetailsResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){

                            if(response.body().getData().getProduct_booked()!=null){
                                txt_order_date.setText(response.body().getData().getProduct_booked());
                            }
                            if(orderid !=null){
                                txt_booking_id.setText(orderid);
                            }

                            if(txt_payment_method!=null){
                                txt_payment_method.setText(""+txt_payment_method);
                            }

                            if(response.body().getData().getProduct_price() !=0){
                                txt_total_order_cost.setText("\u20B9 "+response.body().getData().getProduct_total_price());
                            }
                            if(response.body().getData().getProduct_count() !=0){
                                txt_no_of_items.setText("Item ("+response.body().getData().getProduct_count()+" )");
                            }

                           /* if(response.body().getData().getVendor_complete_info() !=null) {
                                txt_order_dispatch_packdetails.setText(response.body().getData().getVendor_complete_info());
                            }*/
                        /*    if (response.body().getData().getProduct_image() != null && !response.body().getData().getProduct_image().isEmpty()) {
                                Glide.with(getApplicationContext())
                                        .load(response.body().getData().getProduct_image())
                                        .into(img_products_image);
                            }
                            else{
                                Glide.with(getApplicationContext())
                                        .load(APIClient.PROFILE_IMAGE_URL)
                                        .into(img_products_image);

                            }
*/
                            if(response.body().getData().getProdcut_track_details() != null && response.body().getData().getProdcut_track_details().size()>0){
                                prodcutTrackDetailsBeanList = response.body().getData().getProdcut_track_details();


                                for(int i=0; i<prodcutTrackDetailsBeanList.size();i++){
                                    if(prodcutTrackDetailsBeanList.get(i).getTitle()!= null && !prodcutTrackDetailsBeanList.get(i).getTitle().isEmpty()){
                                        Log.w(TAG, " Title " + i+" " + prodcutTrackDetailsBeanList.get(i).getTitle());
                                        switch (prodcutTrackDetailsBeanList.get(i).getTitle()) {
                                            case "Order Booked":
                                                if (prodcutTrackDetailsBeanList.get(i).isStatus()) {
                                                    Log.w(TAG, "Order Booked Date " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                    txt_booked_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                //    txt_booked_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                                                    img_vendor_booked.setImageResource(R.drawable.completed);
                                                }else {
                                                    //txt_booked_date.setText(" ");
                                                    txt_booked_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.coolGrey));
                                                    img_vendor_booked.setImageResource(R.drawable.button_grey_circle);

                                                }

                                                break;
                                            case "Order Accept":
                                                if (prodcutTrackDetailsBeanList.get(i).isStatus()) {
                                                    txt_order_confirm_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                  //  txt_order_confirm_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                                                    img_vendor_confirmed.setImageResource(R.drawable.completed);

                                                } else {
                                                    txt_order_confirm_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.coolGrey));
                                                    img_vendor_confirmed.setImageResource(R.drawable.button_grey_circle);

                                                }

                                                break;
                                            case "Order Dispatch":
                                                if (prodcutTrackDetailsBeanList.get(i).isStatus()) {
                                                    txt_order_dispatch_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                  //  txt_order_dispatch_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                                                    txt_order_transit_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                   // txt_order_transit_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                                                    img_vendor_order_dispatched.setImageResource(R.drawable.completed);
                                                    img_vendor_order_transit.setImageResource(R.drawable.completed);
                                                    txt_order_dispatch_packdetails.setVisibility(View.VISIBLE);
                                                    txt_order_dispatch_packdetails.setText(prodcutTrackDetailsBeanList.get(i).getText());


                                                } else {
                                                    txt_order_dispatch_date.setText(" ");
                                                    txt_order_transit_date.setText(" ");
                                                    img_vendor_order_dispatched.setImageResource(R.drawable.button_grey_circle);
                                                    img_vendor_order_transit.setImageResource(R.drawable.button_grey_circle);
                                                    txt_order_transit_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.coolGrey));
                                                    txt_order_dispatch_date.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.coolGrey));
                                                    txt_order_dispatch_packdetails.setVisibility(View.GONE);

                                                }
                                                break;
                                            case "Order Cancelled":
                                                if (prodcutTrackDetailsBeanList.get(i).isStatus()) {
                                                    ll_order_reject_bypetlover.setVisibility(View.VISIBLE);
                                                    txt_order_reject_date_petlover.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                    txt_order_reject_date_reason.setText(" " + prodcutTrackDetailsBeanList.get(i).getText());
                                                    txt_order_vendor_reject_date_reason.setText(prodcutTrackDetailsBeanList.get(i).getText());
                                                    txt_order_vendor_reject_date_reason.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                                                    img_vendor_order_rejected_bypetlover.setImageResource(R.drawable.ic_baseline_check_circle_24);

                                                } else {
                                                    ll_order_reject_bypetlover.setVisibility(View.GONE);

                                                }

                                                break;
                                            case "Vendor cancelled":
                                                if (prodcutTrackDetailsBeanList.get(i).isStatus()) {
                                                    ll_order_reject.setVisibility(View.VISIBLE);
                                                    txt_order_reject_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());
                                                    txt_order_vendor_reject_date_reason.setText(prodcutTrackDetailsBeanList.get(i).getText());
                                                    txt_order_vendor_reject_date_reason.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                                                    img_vendor_order_rejected.setImageResource(R.drawable.ic_baseline_check_circle_24);


                                                } else {
                                                    ll_order_reject.setVisibility(View.GONE);


                                                }

                                                break;
                                        }


                                    }

                                }

                            }



                        }


                    }

                }


            }

            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onFailure(@NonNull Call<TrackOrderDetailsResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"TrackOrderDetailsResponse flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private TrackOrderDetailsRequest trackOrderDetailsRequest() {
        TrackOrderDetailsRequest trackOrderDetailsRequest = new TrackOrderDetailsRequest();
        trackOrderDetailsRequest.setOrder_id(orderid);
        trackOrderDetailsRequest.setProduct_order_id(_id);
        Log.w(TAG,"trackOrderDetailsRequest"+ "--->" + new Gson().toJson(trackOrderDetailsRequest));
        return trackOrderDetailsRequest;
    }



    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), CustomerDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    private void setMargins(RelativeLayout rl_layout, int i, int i1, int i2, int i3) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)rl_layout.getLayoutParams();
        params.setMargins(i, i1, i2, i3);
        rl_layout.setLayoutParams(params);
    }

    @Override
    public void trackProductListener(String fromactivity, int product_id, String orderid, String TAG, List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> product_details) {

    }
}