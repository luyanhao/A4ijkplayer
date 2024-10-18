/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alanwang4523.a4ijkplayerdemo.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alanwang4523.a4ijkplayerdemo.R;
import com.alanwang4523.a4ijkplayerdemo.activities.VideoActivity;
import com.alanwang4523.a4ijkplayerdemo.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class SampleMediaListFragment extends Fragment {
    private ListView mFileListView;
    private SampleMediaAdapter mAdapter;
    private final int GET_DATA_SUCCESS = 1000;
    private Handler mainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    mAdapter.setDatas(datas);
                    break;
                default:
                    break;
            }
        }
    };
    List<String[]> datas = new ArrayList<>();

    public static SampleMediaListFragment newInstance() {
        SampleMediaListFragment f = new SampleMediaListFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_file_list, container, false);
        mFileListView = (ListView) viewGroup.findViewById(R.id.file_list_view);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();

        mAdapter = new SampleMediaAdapter();
        mFileListView.setAdapter(mAdapter);
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                String[] item = mAdapter.getItem(position);
                String name = item[0];
                String url = item[1];
                VideoActivity.intentTo(activity, url, name);
            }
        });
        new Thread(new MyTask()).start();
    }

    private class MyTask implements Runnable {

        @Override
        public void run() {
            datas = Utils.getUrlsFromAssets(getActivity(), "tv_live_links.txt");
            Message msg = Message.obtain();
            msg.what=GET_DATA_SUCCESS;
            mainHandler.sendMessage(msg);
        }
    }

    final class SampleMediaItem {
        String mUrl;
        String mName;

        public SampleMediaItem(String url, String name) {
            mUrl = url;
            mName = name;
        }
    }

    static final class SampleMediaAdapter extends BaseAdapter {
        public List<String[]> datas = new ArrayList<>();
        public void setDatas(List<String[]> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public String[] getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.mNameTextView = view.findViewById(android.R.id.text1);
                viewHolder.mUrlTextView = view.findViewById(android.R.id.text2);
            }

            String[] item = getItem(position);
            viewHolder.mNameTextView.setText(item[0]);
//            viewHolder.mUrlTextView.setText(item[1]);

            return view;
        }

        static final class ViewHolder {
            public TextView mNameTextView;
            public TextView mUrlTextView;
        }
    }
}
