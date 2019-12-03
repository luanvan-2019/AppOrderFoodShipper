package com.hcmunre.apporderfoodshipper.commons;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hcmunre.apporderfoodshipper.models.entity.Shipper;
import com.hcmunre.apporderfoodshipper.models.entity.ShipperOrder;
import com.hcmunre.apporderfoodshipper.notifications.MySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Common {
    public static Shipper currentShipper;
    public static final String KEY_SHIPPER="data_shipper";
    public static ShipperOrder currrentShipperOrder;
    public static final String TAG="ERROR";
    public static String FCM_API = "https://fcm.googleapis.com/fcm/send";
    public static String serverKey = "key=" + "AAAABxgzzk4:APA91bFOUq0T_vGnwemLQfJcU6akuV1gLQVJdL5mxyxV1m1bDeDbapGb8mWH0gKqSL2tSyuS_A7kTD3iWTfeFK0NhHNhcu8TY7Z7ClSu8LA2xJSJoDaYhbOge7MUF1J8V6FSRiUeDW8i";
    public static String contentType = "application/json";
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static String createTopicSender(String topicChannel) {
        return new StringBuilder("/topics/").append(topicChannel).toString();
    }
    public static String getTopicChannelShippper(int userId) {
        return new StringBuilder("Shipper").append(userId).toString();
    }
    public static void sendNotification(JSONObject notification, final Context context) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Yêu cầu lỗi", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Lỗi");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
