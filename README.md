##### Call 객체를 만들어줄 때의 제네릭은 Response를 받을 객체 타입으로 만들어줘야 함
```java
Call<ResponseBody> call = signUpAPI.insertUser(user);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("====response status", response.message());
                        Log.d("====response", response.code()+"");
                        Log.d("====req", "onResponse: " + response.body().email);
                        //Log.d("response", response.body().toString());
                        Log.d("====성공하였습니다", "성공");
                        //Log.d("gggg", response.body().username + " ");
                    }
```

##### Post를 할 때 잘 되지 않은 이유는 img_profile 필드에 null값을 넣었는데 json으로 변환해줄 때 img_profile 필드가 들어가지 않았음,
##### 다음과 같이 serializeNulls()를 넣어줬더니 들어가짐
```java
interface SignUpAPI{
    String signUpUrl = "http://explog-project-dev.ap-northeast-2.elasticbeanstalk.com";

//        @FormUrlEncoded
    @POST("/member/signup/")
    public Call<ResponseBody> insertUser(
            @Body User user
            );

    //Call<List<User>> signUp(@Body User user);
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
```
