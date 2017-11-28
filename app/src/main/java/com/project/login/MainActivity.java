package com.project.login;

import android.os.AsyncTask;
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
                //User user = new User();
                String email = etEmail.getText().toString();
                String password1 = etPassword.getText().toString();
                String password2 = etPasswordConfirm.getText().toString();
                String username = etNickName.getText().toString();
                String img_profile = null;

                SignUpAPI signUpAPI = SignUpAPI.retrofit.create(SignUpAPI.class);
                Call<ResponseBody> call = signUpAPI.insertUser(password1, password2, email, username, img_profile);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("response status", response.message());
                        Log.d("response", response.code()+"");
                        //Log.d("response", response.body().toString());
                        Log.d("성공하였습니다", "성공");
                        //Log.d("gggg", response.body().username + " ");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("실패하였습니다", "실패");
                    }
                });

//                new AsyncTask<Call, Void, String>(){
//                    @Override
//                    protected String doInBackground(Call[] calls) {
//
//                        try {
//                            return calls[0].execute().body().toString();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(String s) {
//                        Log.d("내용", s);
//                    }
//                }.execute(call);
            }
        });
    }

    interface SignUpAPI{
        String signUpUrl = "http://explog-project-dev.ap-northeast-2.elasticbeanstalk.com";

        @FormUrlEncoded
        @POST("/member/signup/")
        public Call<ResponseBody> insertUser(
                @Field("password1") String password1,
                @Field("password2") String password2,
                @Field("email") String email,
                @Field("username") String username,
                @Field("img_profile") String img_profile
                );

        //Call<List<User>> signUp(@Body User user);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(signUpUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
