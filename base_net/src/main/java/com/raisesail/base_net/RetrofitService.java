package com.raisesail.base_net;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
/**
 * 全局公用接口
 */
public interface RetrofitService {
    //获取json数据
    @Headers({"Content-Type: application/json","Accept: application/json"})
    //@POST("")
    Call<ResponseBody> getJsonMessage(@Body RequestBody info);
    //body为音频数据
    @Headers({"Content-Type: application/json","Accept: audio/mpeg"})
    //@POST("")
    Call<ResponseBody> getMpegData(@Body RequestBody info);
    //表单上传
    @Headers({"Content-Type':'application/x-www-form-urlencoded","Accept: application/json"})
    //@POST("")
    Call<ResponseBody> postFormData(@Body RequestBody info);

}
