package com.alanwang4523.a4ijkplayerdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alanwang4523.a4ijkplayerdemo.R;
import com.alanwang4523.a4ijkplayerdemo.application.MyFlutterEngine;

import io.flutter.embedding.android.FlutterFragment;

public class FlutterFragmentAct extends AppCompatActivity {
    private static String FRAGMENT_TAG = "flutter_fragment";
    private FlutterFragment flutterFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutterfragmet);

        flutterFragment = (FlutterFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (flutterFragment == null) {
            flutterFragment = getFlutterFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.body, flutterFragment, FRAGMENT_TAG)
                .commit();
    }

    private FlutterFragment getFlutterFragment() {
        return FlutterFragment
                .withCachedEngine(MyFlutterEngine.getEngineId(this, null))
                .build();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (flutterFragment != null) {
            flutterFragment.onPostResume();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (flutterFragment != null) {
            flutterFragment.onNewIntent(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (flutterFragment != null) {
            flutterFragment.onBackPressed();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (flutterFragment != null) {
            flutterFragment.onUserLeaveHint();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (flutterFragment != null) {
            flutterFragment.onTrimMemory(level);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (flutterFragment != null) {
            flutterFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
