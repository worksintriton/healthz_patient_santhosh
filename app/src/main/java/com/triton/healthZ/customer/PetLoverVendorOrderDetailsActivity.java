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
import com.triton.healthz.doctor.shop.DoctorTrackOrderActivity;
import com.triton.healthz.interfaces.TrackProductListener;
import com.triton.healthz.requestpojo.PetLoverVendorOrderDetailsRequest;
import com.triton.healthz.responsepojo.PetLoverVendorOrderDetailsResponse;
import com.triton.healthz.serviceprovider.shop.SPTrackOrderActivity;
import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetLoverVendorOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, TrackProductListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "PetLoverVendorOrderDetailsActivity" ;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_status)
    TextView txt_order_status;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_delivered_date)
    TextView txt_delivered_date;

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
    @BindView(R.id.txt_quantity)
    TextView txt_quantity;

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_order_status)
    ImageView img_order_status;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

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
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_of_prodslabel)
    TextView txt_no_of_prodslabel;

    private String _id;
    private String fromactivity;

    private  boolean button1IsVisible = false;
    private  boolean ShippingIsVisible = false;
    private  boolean productsIsVisible = false;
    private String orderid;
    private List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> productdetailslist;
    private List<Integer> product_id = new ArrayList<>();


/**/    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_original_price)
    LinearLayout ll_original_price;

/*    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_discount_price)
    LinearLayout ll_discount_price;*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_original_price)
    TextView txt_original_price;
//
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.txt_discount_price)
//    TextView txt_discount_price;

    private int Order_price;

    /* Petlover Bottom Navigation */

    BottomNavigationView bottom_navigation_view;
    FloatingActionButton floatingActionButton;


    @SuppressLint({"LogNotTimber", "LongLogTag", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlover_vendor_order_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getString("_id");
            orderid = _id;
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"_id : "+_id+" fromactivity : "+ fromactivity);

        }

        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.order_details));
        img_sos.setVisibility(View.INVISIBLE);
        img_cart.setVisibility(View.INVISIBLE);
        img_profile.setVisibility(View.INVISIBLE);
        img_notification.setVisibility(View.INVISIBLE);



        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(getApplicationContext(), CustomerProfileScreenActivity.class));
                intent.putExtra("_id",_id);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(this);





        scrollablContent.setVisibility(View.GONE);
        txt_cancell_order.setVisibility(View.GONE);

        if (new ConnectionDetector(PetLoverVendorOrderDetailsActivity.this).isNetworkAvailable(PetLoverVendorOrderDetailsActivity.this)) {
            vendorOrderDetailsResponseCall();

        }

        img_expand_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG, "button1IsVisible : "+button1IsVisible);

                if(button1IsVisible) {
                    ll_orderdetails.setVisibility(View.VISIBLE);
                    img_expand_arrow.setImageResource(R.drawable.ic_up);
                    button1IsVisible = false;
                }
                else {
                    ll_orderdetails.setVisibility(View.GONE);
                    img_expand_arrow.setImageResource(R.drawable.icnhzdwnarw);
                    button1IsVisible = true;

                }


            }
        });
        img_expand_arrow_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG, "ShippingIsVisible : "+ShippingIsVisible);

                if(ShippingIsVisible) {
                    ll_shippingaddress.setVisibility(View.VISIBLE);
                    img_expand_arrow_shipping.setImageResource(R.drawable.ic_up);
                    ShippingIsVisible = false;
                } else {
                    ll_shippingaddress.setVisibility(View.GONE);
                    img_expand_arrow_shipping.setImageResource(R.drawable.icnhzdwnarw);
                    ShippingIsVisible = true;

                }


            }
        });
        img_expand_arrow_productdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG, "productsIsVisible : "+productsIsVisible);

                if(productsIsVisible) {
                    ll_productdetails.setVisibility(View.GONE);
                    img_expand_arrow_productdetails.setImageResource(R.drawable.ic_up);
                    productsIsVisible = false;
                } else {
                    ll_productdetails.setVisibility(View.VISIBLE);
                    img_expand_arrow_productdetails.setImageResource(R.drawable.icnhzdwnarw);
                    productsIsVisible = true;

                }


            }
        });
        txt_cancell_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), PetVendorCancelOrderActivity.class);
                i.putExtra("orderid", orderid);
                i.putIntegerArrayListExtra("product_idList", (ArrayList<Integer>) product_id );
                i.putExtra("fromactivity", fromactivity);
                i.putExtra("cancelorder", "bulk");
                i.putExtra("Order_price", Order_price);
                startActivity(i);

            }
        });

        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottomNavigation);
        floatingActionButton = include_petlover_footer.findViewById(R.id.fab);
     //   bottom_navigation_view.setItemIconTintList(null);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),PetMyOrdrersNewActivity.class));
        finish();
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.img_back:
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
    private void vendorOrderDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetLoverVendorOrderDetailsResponse> call = apiInterface.get_product_list_by_petlover_ResponseCall(RestUtils.getContentType(), petLoverVendorOrderDetailsRequest());
        Log.w(TAG,"vendorOrderDetailsResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PetLoverVendorOrderDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<PetLoverVendorOrderDetailsResponse> call, @NonNull Response<PetLoverVendorOrderDetailsResponse> response) {

                Log.w(TAG,"vendorOrderDetailsResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        scrollablContent.setVisibility(View.VISIBLE);

                        if(response.body().getData()!=null){

                            if(response.body().getData().getOrder_details() != null){
                                ll_original_price.setVisibility(View.VISIBLE);

                               /* if(response.body().getData().getOrder_details().getCoupon_status() != null && response.body().getData().getOrder_details().getCoupon_status().equalsIgnoreCase("Applied")){

                              //      ll_discount_price.setVisibility(View.VISIBLE);

                                    if(response.body().getData().getOrder_details().getCoupon_discount_price() != 0){
                                    //    txt_discount_price.setText("\u20B9 "+response.body().getData().getOrder_details().getCoupon_discount_price());
                                    }

                                }
                                else{
                                    ll_original_price.setVisibility(View.GONE);
                              //      ll_discount_price.setVisibility(View.GONE);
                                }*/
                                Log.w(TAG,"original_price"+ "--->" +  response.body().getData().getOrder_details().getOrder_price());

                                if(response.body().getData().getOrder_details().getOrder_price() != 0){
                                    txt_original_price.setText("\u20B9 "+response.body().getData().getOrder_details().getOrder_price());
                                }
                            }


                            if(response.body().getData().getOrder_details().getOrder_text() !=null && !response.body().getData().getOrder_details().getOrder_text().isEmpty()){
                              //  txt_product_title.setText(response.body().getData().getOrder_details().getOrder_text());
                            }
                            if(response.body().getData().getOrder_details().getOrder_price()!=0){
                                Order_price = response.body().getData().getOrder_details().getOrder_price();
                               // txt_products_price.setText("\u20B9 "+response.body().getData().getOrder_details().getOrder_price());
                            }

                            if (response.body().getData().getOrder_details().getOrder_price() != 0 && response.body().getData().getOrder_details().getOrder_product() != 0) {
                                if (response.body().getData().getOrder_details().getOrder_product() == 1) {
                                    Order_price = response.body().getData().getOrder_details().getOrder_price();
                                 //   txt_products_price.setText("\u20B9 " + response.body().getData().getOrder_details().getOrder_price() + " (" + response.body().getData().getOrder_details().getOrder_product() + " product )");
                                } else {
                                    Order_price = response.body().getData().getOrder_details().getOrder_price();
                                   // txt_products_price.setText("\u20B9 " + response.body().getData().getOrder_details().getOrder_price() + " (" + response.body().getData().getOrder_details().getOrder_product() + " products )");

                                }
                            }
                            else {
                                if (response.body().getData().getOrder_details().getOrder_product() == 1) {
                                    Order_price = 0;
                                    //txt_products_price.setText("\u20B9 " + 0 + " (" + response.body().getData().getOrder_details().getOrder_product() + " product )");
                                } else {
                                    Order_price = 0;
                                    //txt_products_price.setText("\u20B9 " + 0 + " (" + response.body().getData().getOrder_details().getOrder_product() + " products )");
                                }
                            }





                            if(response.body().getData().getOrder_details().getOrder_booked()!=null){
                                txt_order_date.setText(response.body().getData().getOrder_details().getOrder_booked());
                            }
                            if(response.body().getData().getOrder_details().getOrder_id()!=null){
                                txt_booking_id.setText(response.body().getData().getOrder_details().getOrder_id());

                            }
                            if(response.body().getData().getOrder_details().getOrder_price() !=0){
                                txt_total_order_cost.setText("\u20B9 "+response.body().getData().getOrder_details().getOrder_price());
                            }
                            if(response.body().getData().getOrder_details().getOrder_product() !=0){

                                if(response.body().getData().getOrder_details().getOrder_product()==1){

                                    txt_no_of_prodslabel.setText("Number of Product");

                                    txt_no_of_items.setText("Item ( "+response.body().getData().getOrder_details().getOrder_product()+" )");

                                }
                                else {

                                    txt_no_of_prodslabel.setText("Number of Products");

                                    txt_no_of_items.setText("Items ( "+response.body().getData().getOrder_details().getOrder_product()+" )");

                                }
                                txt_quantity.setText(""+response.body().getData().getOrder_details().getOrder_product()+" Product");

                            }

                            if(response.body().getData().getShipping_address() !=null){
                           //     txt_shipping_address_name.setText(response.body().getData().getShipping_address().getUser_name());
                            //    txt_shipping_address_city.setText(response.body().getData().getShipping_address().getShipping_location());
                                txt_shipping_address_street.setText(response.body().getData().getShipping_address().getLocation_title());
                         //       txt_shipping_address_state_pincode.setVisibility(View.GONE);
                          /*     if(response.body().getData().getShipping_address().getUser_phone() != null && !response.body().getData().getShipping_address().getUser_phone().isEmpty()) {
                                   txt_shipping_address_landmark.setText("Phone : " + response.body().getData().getShipping_address().getUser_phone());
                               }
                                if(response.body().getData().getShipping_address().getLand_mark() != null && !response.body().getData().getShipping_address().getLand_mark().isEmpty()) {
                                    txt_shipping_address_phone.setText("Landmark : " + response.body().getData().getShipping_address().getLand_mark());
                                }
*/
                            }

//                            if (response.body().getData().getOrder_details().getOrder_image() != null && !response.body().getData().getOrder_details().getOrder_image().isEmpty()) {
//                                Glide.with(getApplicationContext())
//                                        .load(response.body().getData().getOrder_details().getOrder_image())
//                                        .into(img_products_image);
//                            }
//                            else{
//                                Glide.with(getApplicationContext())
//                                        .load(APIClient.PROFILE_IMAGE_URL)
//                                        .into(img_products_image);
//
//                            }

                            if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverNewOrders")){
                                txt_order_status.setText("Booked for");
                                img_order_status.setImageResource(R.drawable.completed);
                                if(response.body().getData().getOrder_details().getOrder_booked() != null){
                                    txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_booked());
                                }
                            }
                            else if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCompletedOrders")){
                                txt_order_status.setText("Delivered on");
                                img_order_status.setImageResource(R.drawable.completed);
                                if(response.body().getData().getOrder_details().getOrder_completed() != null){
                                    txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_completed());
                                }
                            }
                            else if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCancelledOrders")) {
                                txt_order_status.setText("Cancelled on");
                                img_order_status.setImageResource(R.drawable.ic_baseline_cancel_24);
                                if (response.body().getData().getOrder_details().getOrder_cancelled() != null && !response.body().getData().getOrder_details().getOrder_cancelled().isEmpty()) {
                                    txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_cancelled());
                                }


                            }else{
                                if(response.body().getData().getOrder_details().getOrder_status() != null && response.body().getData().getOrder_details().getOrder_status().equalsIgnoreCase("Cancelled")){
                                    txt_order_status.setText("Cancelled on");
                                    img_order_status.setImageResource(R.drawable.ic_baseline_cancel_24);
                                    if (response.body().getData().getOrder_details().getOrder_cancelled() != null && !response.body().getData().getOrder_details().getOrder_cancelled().isEmpty()) {
                                        txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_cancelled());
                                    }
                                }
                            }

                            if(response.body().getData().getProduct_details() != null && response.body().getData().getProduct_details().size()>0){
                                productdetailslist = response.body().getData().getProduct_details();
                                Log.w(TAG,"ProductsDetails size : "+productdetailslist.size()+" productdetails "+new Gson().toJson(productdetailslist));
                                for(int i=0; i<productdetailslist.size();i++){
                                    int productid = response.body().getData().getProduct_details().get(i).getProduct_id();
                                    String productstatus =  response.body().getData().getProduct_details().get(i).getProduct_stauts();
                                    product_id.add(productid);
                                    if(productstatus != null){
                                        if(productstatus.equalsIgnoreCase("Order Booked")){
                                            txt_cancell_order.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }

                                if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCancelledOrders")) {
                                    txt_cancell_order.setVisibility(View.GONE);
                                }
                                Log.w(TAG,"product_id List : "+new Gson().toJson(product_id));

                                rv_productdetails.setVisibility(View.VISIBLE);
                                txt_no_products.setVisibility(View.GONE);
                                setView(response.body().getData().getProduct_details());
                            }else{
                                rv_productdetails.setVisibility(View.GONE);
                                txt_no_products.setVisibility(View.VISIBLE);
                                txt_no_products.setText("No products found");
                            }






                        }


                    }

                }


            }

            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onFailure(@NonNull Call<PetLoverVendorOrderDetailsResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"VendorOrderDetailsResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private PetLoverVendorOrderDetailsRequest petLoverVendorOrderDetailsRequest() {
        PetLoverVendorOrderDetailsRequest petLoverVendorOrderDetailsRequest = new PetLoverVendorOrderDetailsRequest();
        petLoverVendorOrderDetailsRequest.setOrder_id(_id);
        Log.w(TAG,"vendorOrderDetailsRequest"+ "--->" + new Gson().toJson(petLoverVendorOrderDetailsRequest));
        return petLoverVendorOrderDetailsRequest;
    }
    private void setView(List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> product_details) {
        rv_productdetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_productdetails.setItemAnimator(new DefaultItemAnimator());
        ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter(getApplicationContext(),product_details,orderid,fromactivity,this,true);
        rv_productdetails.setAdapter(productDetailsAdapter);

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

        if(fromactivity != null) {
            if (fromactivity.equalsIgnoreCase("FragmentDoctorNewOrders") || fromactivity.equalsIgnoreCase("FragmentDoctorCompletedOrders") || fromactivity.equalsIgnoreCase("FragmentDoctorCancelledOrders")) {
                Intent i = new Intent(PetLoverVendorOrderDetailsActivity.this, DoctorTrackOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", product_id);
                i.putExtra("orderid", orderid);
                i.putExtra("fromactivity", TAG);
               startActivity(i);
            }
            else if (fromactivity.equalsIgnoreCase("FragmentSPNewOrders") || fromactivity.equalsIgnoreCase("FragmentSPCompletedOrders") || fromactivity.equalsIgnoreCase("FragmentSPCancelledOrders")) {
                Intent i = new Intent(PetLoverVendorOrderDetailsActivity.this, SPTrackOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", product_id);
                i.putExtra("orderid", orderid);
                i.putExtra("fromactivity", TAG);
           startActivity(i);
            }
            else {
                Intent i = new Intent(PetLoverVendorOrderDetailsActivity.this, TrackOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", product_id);
                i.putExtra("product_details",(Serializable) product_details);
                i.putExtra("payment_method",txt_payment_method.getText().toString());
                i.putExtra("orderid", orderid);
                i.putExtra("fromactivity", TAG);
              startActivity(i);
            }
        }else{
            Intent i = new Intent(PetLoverVendorOrderDetailsActivity.this, TrackOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("_id", product_id);
            i.putExtra("product_details",(Serializable) product_details);
            i.putExtra("payment_method",txt_payment_method.getText().toString());
            i.putExtra("orderid", orderid);
            i.putExtra("fromactivity", TAG);
         startActivity(i);
        }



    }

}