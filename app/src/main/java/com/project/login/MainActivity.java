package com.project.login;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;
    @BindView(R.id.etNickName)
    EditText etNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
    }

    private void setListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.email = etEmail.getText().toString();
                user.password1 = etPassword.getText().toString();
                user.password2 = etPasswordConfirm.getText().toString();
                user.username = etNickName.getText().toString();
                user.img_profile = null;

                SignUpAPI signUpAPI = SignUpAPI.retrofit.create(SignUpAPI.class);

                Call<ResponseBody> call = signUpAPI.insertUser(user);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Log.d("====response status", response.message());
//                        Log.d("====response", response.code()+"");
//                        Log.d("====req", "onResponse: " + response.body().email);
//                        Log.d("====성공하였습니다", "성공");
                        //Log.d("gggg", response.body().username + " ");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("====실패하였습니다", "실패");
                    }
                });
            }
        });
    }

    interface SignUpAPI{
        String signUpUrl = "http://explog-project-dev.ap-northeast-2.elasticbeanstalk.com";

        @POST("/member/signup/")
        public Call<ResponseBody> insertUser(
                @Body User user
                );

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(signUpUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
