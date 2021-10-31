package com.triton.healthZ.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.triton.healthZ.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeScreenActivity extends AppCompatActivity implements View.OnClickListener {


    private String TAG = "WelcomeScreenActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    Button btn_login;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_signup)
    Button btn_signup;

    String fromactivity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_welcomemsg)
    TextView txt_welcomemsg;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");

        String text = "<font color=#575757>Welcome to</font> ";

        String text1 = "<font color=#1E0702> HealthZ </font><font color=#575757>shopping</font> ";

        String textf = text + "\n" + text1;

        txt_welcomemsg.setText(Html.fromHtml(textf));

        btn_login.setOnClickListener(this);

    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_login) {
            gotoLoginActivity();
        }
    }

    private void gotoLoginActivity() {

        Intent intent = new Intent(WelcomeScreenActivity.this, VerifyPhoneNumberActivity.class);
        intent.putExtra("fromactivity", TAG);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        //Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}