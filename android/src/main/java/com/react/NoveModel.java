package com.react;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Base64;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

//nove model to be injected inside js
public class NoveModel extends ReactContextBaseJavaModule {
    Context context;
     AudioConverter adapter;
     Gson gson = new Gson();


//constructor
    public NoveModel(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;

    }

//method to be called inside js class
    @ReactMethod
       public void playAudio(Integer rate, String input) {
           if (adapter == null) {
               adapter = new AudioConverter(rate);
           }
         try {

//             byte[] audioData = Base64.decode(input, 16);
                 byte[] audioData = gson.fromJson(input,byte[].class);
                 adapter.writeArrayToPlayer(audioData);
           }catch (Exception e){
          e.printStackTrace();
        }
       }

    @NonNull
    @Override
    public String getName() {
        return "Nove";
    }
}
