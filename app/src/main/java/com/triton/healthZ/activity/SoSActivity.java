package com.triton.healthz.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.triton.healthz.R;

import com.triton.healthz.adapter.PetLoverSOSAdapter;
import com.triton.healthz.api.APIClient;
import com.triton.healthz.api.RestApiInterface;
import com.triton.healthz.customer.CustomerDashboardActivity;
import com.triton.healthz.interfaces.SoSCallListener;

import com.triton.healthz.requestpojo.SOSListRequest;
import com.triton.healthz.responsepojo.SOSListResponse;
import com.triton.healthz.sessionmanager.SessionManager;

import com.triton.healthz.utils.ConnectionDetector;
import com.triton.healthz.utils.RestUtils;

import com.wang.avi.AVLoadingIndicatorView;


import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SoSActivity extends AppCompatActivity implements SoSCallListener {

    String TAG = "SoSActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_sosnumbers)
    RecyclerView rv_sosnumbers;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_call)
    Button btn_call;





    SessionManager session;
    String type = "",name = "",userid = "";
    private String fromactivity;

    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;
    private Dialog alertDialog;

    List<SOSListResponse.DataBean> sosList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
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
        toolbar_title.setText(getResources().getString(R.string.sos));


        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(userid != null) {
            if (new ConnectionDetector(SoSActivity.this).isNetworkAvailable(SoSActivity.this)) {
                SOSListResponseCall();
            }
        }










        btn_call.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SoSActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                gotoPhone();
            }

        });



    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void SOSListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SOSListResponse> call = apiInterface.SOSListResponseCall(RestUtils.getContentType(), sosListRequest());
        Log.w(TAG,"SOSListResponseCall url  :%s"+" "+ call.request().url().toString());
        call.enqueue(new Callback<SOSListResponse>() {
            @Override
            public void onResponse(@NonNull Call<SOSListResponse> call, @NonNull Response<SOSListResponse> response) {

                Log.w(TAG,"vendor_product_create_ResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                  if(response.body().getData() != null && response.body().getData().size()>0){
                      sosList =  response.body().getData();
                    rv_sosnumbers.setVisibility(View.VISIBLE);
                    btn_call.setVisibility(View.VISIBLE);
                    txt_no_records.setVisibility(View.GONE);
                    rv_sosnumbers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv_sosnumbers.setItemAnimator(new DefaultItemAnimator());
                    PetLoverSOSAdapter petLoverSOSAdapter = new PetLoverSOSAdapter(getApplicationContext(), sosList,SoSActivity.this);
                    rv_sosnumbers.setAdapter(petLoverSOSAdapter);
                }
                else{
                    rv_sosnumbers.setVisibility(View.GONE);
                    btn_call.setVisibility(View.GONE);
                    txt_no_records.setVisibility(View.VISIBLE);
                    txt_no_records.setText(getResources().getString(R.string.no_phone_numbers));

                }
                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SOSListResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"SOSListResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private SOSListRequest sosListRequest() {

        /*
         * user_id : 60a5df0f785e571920ac46f0
          */
        SOSListRequest sosListRequest = new SOSListRequest();
        sosListRequest.setUser_id(userid);
        Log.w(TAG,"sosListRequest"+ "--->" + new Gson().toJson(sosListRequest));
        return sosListRequest;
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    private void gotoPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sosPhonenumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }








    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("CustomerNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), CustomerDashboardActivity.class));
            finish();
        }else{
            startActivity(new Intent(getApplicationContext(), CustomerDashboardActivity.class));
            finish();
        }

    }


    @Override
    public void soSCallListener(String phonenumber) {
        if(phonenumber != null){
            sosPhonenumber = phonenumber;
        }
    }
}
