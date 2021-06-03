package au.edu.federation.itech3107.fedunimillionaire30336872;

import android.content.Context;
import android.security.keystore.StrongBoxUnavailableException;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InternetConnect {

         private static String results;
         public static boolean disconnect= true;

            public static void get(Context context, String websites, final VolleyCallback callback) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, websites, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        results = json;
                        callback.onSuccess(results);
                        Log.e("internet",results);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        disconnect = false;
                        callback.onSuccess("");
                    }
                });
                requestQueue.add(stringRequest);
            }


            public interface VolleyCallback {
                void onSuccess(String result);
            }
}


