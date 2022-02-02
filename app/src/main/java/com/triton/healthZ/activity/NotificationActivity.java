package com.triton.healthz.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.triton.healthz.R;
import com.triton.healthz.adapter.NotificationDashboardAdapter;

import com.triton.healthz.api.APIClient;
import com.triton.healthz.api.RestApiInterface;
import com.triton.healthz.customer.CustomerProfileScreenActivity;
import com.triton.healthz.doctor.DoctorDashboardActivity;
import com.triton.healthz.customer.CustomerDashboardActivity;
import com.triton.healthz.requestpojo.NotificationGetlistRequest;
import com.triton.healthz.requestpojo.NotificationsMarkRequest;
import com.triton.healthz.responsepojo.NotificationGetlistResponse;
import com.triton.healthz.responsepojo.SuccessResponse;
import com.triton.healthz.serviceprovider.ServiceProviderDashboardActivity;
import com.triton.healthz.sessionmanager.SessionManager;
import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;
import com.triton.healthz.vendor.VendorDashboardActivity;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvNoRecords)
    TextView tvNorecords;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvnotifiaction)
    RecyclerView rvnotifiaction;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;





    private String TAG = "NotificationActivity";

    SessionManager session;
    String type = "",name = "",userid = "";
    private List<NotificationGetlistResponse.DataBean> notificationGetlistResponseList;
    private String fromactivity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaton);
        Log.w(TAG,"onCreate-->");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"Bundle "+" fromactivity : "+fromactivity);



        }



        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        type = user.get(SessionManager.KEY_TYPE);
        name = user.get(SessionManager.KEY_FIRST_NAME);
        userid = user.get(SessionManager.KEY_ID);


        Log.w(TAG,"session--->"+"type :"+type+" "+"name :"+" "+name);



        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.notification));
        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        img_profile.setVisibility(View.GONE);




        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerProfileScreenActivity.class);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (new ConnectionDetector(NotificationActivity.this).isNetworkAvailable(NotificationActivity.this)) {
             notificationGetlistResponseCall();
        }

        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (new ConnectionDetector(NotificationActivity.this).isNetworkAvailable(NotificationActivity.this)) {
                   notificationGetlistResponseCall();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @SuppressLint("LogNotTimber")
    private void notificationMarkResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.notificationMarkResponseCall(RestUtils.getContentType(),notificationsMarkRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"notificationMarkResponseCall SuccessResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"notificationMarkResponseCall SuccessResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private NotificationsMarkRequest notificationsMarkRequest() {
        /*
         * user_id : 5ee3666a5dfb34019b13c3a2
         */
        NotificationsMarkRequest notificationsMarkRequest = new NotificationsMarkRequest();
        notificationsMarkRequest.setUser_id(userid);
        Log.w(TAG,"notificationsMarkRequest"+ "--->" + new Gson().toJson(notificationsMarkRequest));
        return notificationsMarkRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("CustomerNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), CustomerDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("DoctorNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), DoctorDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("VendorNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), VendorDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("ServiceProviderNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), ServiceProviderDashboardActivity.class));
            finish();
        }else{
            finish();
        }

    }
    private void setView() {
        rvnotifiaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvnotifiaction.setItemAnimator(new DefaultItemAnimator());
        NotificationDashboardAdapter notificationDashboardAdapter = new NotificationDashboardAdapter(getApplicationContext(), notificationGetlistResponseList);
        rvnotifiaction.setAdapter(notificationDashboardAdapter);

    }

    @SuppressLint("LogNotTimber")
    private void notificationGetlistResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<NotificationGetlistResponse> call = ApiService.notificationGetlistResponseCall(RestUtils.getContentType(),notificationGetlistRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<NotificationGetlistResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<NotificationGetlistResponse> call, @NonNull Response<NotificationGetlistResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"NotificationGetlistResponse"+ "--->" + new Gson().toJson(response.body()));
                refresh_layout.setRefreshing(false);

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        notificationMarkResponseCall();

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            notificationGetlistResponseList = response.body().getData();
                            tvNorecords.setVisibility(View.GONE);
                            rvnotifiaction.setVisibility(View.VISIBLE);
                            setView();
                        }else{
                            rvnotifiaction.setVisibility(View.GONE);
                            tvNorecords.setVisibility(View.VISIBLE);
                            tvNorecords.setText(getResources().getString(R.string.no_notifications));

                        }


                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<NotificationGetlistResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"NotificationGetlistResponse"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private NotificationGetlistRequest notificationGetlistRequest() {
        /*
         * user_id : 5ee3666a5dfb34019b13c3a2
         */
        NotificationGetlistRequest notificationGetlistRequest = new NotificationGetlistRequest();
        notificationGetlistRequest.setUser_id(userid);
        Log.w(TAG,"notificationGetlistRequest"+ "--->" + new Gson().toJson(notificationGetlistRequest));
        return notificationGetlistRequest;
    }
}
