package com.example.ztz.openeyesvideos.widgets.audio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by ztz on 2018/7/31.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> list = new VoiceTemplate()
                .prefix("success")
                .numString("22.05")
                .suffix("yuan")
                .gen();

        VoiceSpeaker.getInstance().speak(list);
    }
}
