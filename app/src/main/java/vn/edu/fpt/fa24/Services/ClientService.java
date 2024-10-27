package vn.edu.fpt.fa24.Services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class ClientService<T> {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String BASE_API = "http://10.0.2.2:7115/api/"; // Adjusted for Android emulator
    private JSONObject data = new JSONObject();
    private String jsonData = "";
    private String url;
    private OkHttpClient client;

    private Gson gson = new GsonBuilder().create();

    public ClientService(String url) {
        this.url = BASE_API + url;
        this.client = new OkHttpClient();
    }

    public void setJsonData(String data) {
        this.jsonData = data;
    }

    // Method to add parameters to the request body
    public void addParam(String key, String value) {
        try {
            data.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method to execute a POST request
    public void executePost(final ResponseCallBack<String> callback) {
        String validData = jsonData.isEmpty() ? data.toString() : jsonData;
        RequestBody body = RequestBody.create(validData, JSON);
        Log.i("post", validData);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";
                    Log.e("error", errorMessage);
                    if(errorMessage.length() > 20) {
                        callback.onFailure("Unknown error");
                    } else {
                        callback.onFailure(errorMessage);
                    }
                }
            }
        });
    }

    // Method to execute a PUT request
    public void executePut(final ResponseCallBack<String> callback) {
        String validData = jsonData.isEmpty() ? data.toString() : jsonData;
        RequestBody body = RequestBody.create(validData, JSON);
        Log.i("put", validData);

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";

                    Log.e("error", errorMessage);
                    if(errorMessage.length() > 20) {
                        callback.onFailure("Unknown error");
                    } else {
                        callback.onFailure(errorMessage);
                    }
                }
            }
        });
    }

    // Method to execute a DELETE request
    public void executeDelete(final ResponseCallBack<String> callback) {
        String validData = jsonData.isEmpty() ? data.toString() : jsonData;
        RequestBody body = RequestBody.create(validData, JSON);
        Log.i("delete", validData);

        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";
                    Log.e("error", errorMessage);
                    if(errorMessage.length() > 20) {
                        callback.onFailure("Unknown error");
                    } else {
                        callback.onFailure(errorMessage);
                    }
                }
            }
        });
    }

    // Method to execute a GET request
    public void executeGet(final ResponseCallBack<String> callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    Log.i("get", responseData);
                    callback.onSuccess(responseData);
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "Unknown error";
                    callback.onFailure(errorMessage);
                }
            }
        });
    }
}