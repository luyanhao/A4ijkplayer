package com.alanwang4523.a4ijkplayerdemo.application;

import android.content.Context;
import android.util.Log;

import java.io.File;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class MyFlutterEngine {
    public static FlutterEngine INSTANCE;
    public static String ENGINE_ID = "my_flutter_engine";

    public static String getEngineId(Context context, String initialRoute) {
        init(context, initialRoute);
        return ENGINE_ID;
    }

    public static void init(Context context, String initialRoute) {
        boolean isInvalid = new File(".soFileDir", ".soFileName").exists();
        if (isInvalid) {
            Log.i("bqt", "move .so file");
            INSTANCE.destroy();  // Cleans up all components within this and destroys the associated Dart Isolate
            INSTANCE = null; // This FlutterEngine instance should be discarded after invoking this method
        }
        if (INSTANCE == null) {
            synchronized (MyFlutterEngine.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlutterEngine(context);
                }
            }
            initFlutterEngine(INSTANCE, initialRoute);
        }
    }

    private static void initFlutterEngine(FlutterEngine flutterEngine, String initialRoute) {
        INSTANCE = flutterEngine;
        if (initialRoute != null) {
            flutterEngine.getNavigationChannel().setInitialRoute(initialRoute); // 为缓存的 FlutterEngine 配置自定义的初始路由
        }
        DartExecutor.DartEntrypoint dartEntrypoint = DartExecutor.DartEntrypoint.createDefault();
        flutterEngine.getDartExecutor().executeDartEntrypoint(dartEntrypoint); // Start executing Dart code to pre-warm the FlutterEngine
        // FlutterEngine 其实是保存在一个单例对象的 Map 中，可以根据自己的需求增删改查
        FlutterEngineCache.getInstance() // Static singleton cache that holds FlutterEngine instances identified by String
                .put(ENGINE_ID, flutterEngine); // 其他 API：get/remove/contains/clear
    }
}
