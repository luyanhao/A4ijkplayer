package com.alanwang4523.a4ijkplayerdemo.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {
    public static ArrayList<String[]> getUrlsFromAssets(Context context, String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            ArrayList<String[]> Result=new ArrayList<>();
            while((line = bufReader.readLine()) != null){
                String[] item = line.split(",");
                Result.add(item);
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
