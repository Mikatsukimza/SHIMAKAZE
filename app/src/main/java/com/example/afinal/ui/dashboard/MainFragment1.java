package com.example.afinal.ui.dashboard;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.Weather;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainFragment1 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainFragment1 newInstance(String param1, String param2) {
        MainFragment1 fragment = new MainFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_fragment1, container, false);
//
//
//        final ListView listview = getActivity().findViewById(R.id.lvWeather);
//        final List<String> listdata = new ArrayList<String>();
//
//        final OkHttpClient okclient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10,TimeUnit.SECONDS)
//                .writeTimeout(10,TimeUnit.SECONDS)
//                .build();
//
//        DownloadManager.Request request = new Request.Builder()
//                .url("https://free-api.heweather.com/s6/weather/now?key=e1984444a03d4a52a1f6cc545cce9245&location=beijing")
//                .build();
//
//        Call call=okclient.newCall(request);
//
//        Button btn=root.findViewById(R.id.btWeather);
//        final EditText editText=root.findViewById(R.id.etWeather);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String tar = String.valueOf(editText.getText());
//                String target= Pinyin.toPinyin(tar,"");
//
//                Request request = new Request.Builder()
//                        .url("https://free-api.heweather.com/s6/weather/now?key=e1984444a03d4a52a1f6cc545cce9245&location=" + target)
//                        .build();
//
//                Call call = okclient.newCall(request);
//
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                    }
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        final String htmlStr = response.body().string();
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Gson gson = new Gson();
//                                Weather weather = gson.fromJson(htmlStr, Weather.class);
//                                List<Weather.HeWeather6Bean> local = weather.getHeWeather6();
//
//                                if(local.get(0).getStatus().equals("ok")){
//                                    String str[] = new String[6];
//
//                                    str[0] = "id:" + local.get(0).getBasic().getCid();
//                                    str[1] = "位置:" + local.get(0).getBasic().getLocation();
//                                    str[2] = "国家:" + local.get(0).getBasic().getCnty();
//                                    str[3] = "时间:" + local.get(0).getUpdate().getLoc();
//                                    str[4] = "天气:" + local.get(0).getNow().getCond_txt();
//                                    str[5] = "Tmp" + local.get(0).getNow().getTmp();
//
//                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, str);
//                                    listview.setAdapter(arrayAdapter);
//                                }else {
//                                    Toast.makeText(getActivity(),"查询失败",Toast.LENGTH_LONG).show();
//                                    String str[]=new String[6];
//
//                                    str[5]="id:null";
//                                    str[0]="位置:null";
//                                    str[1]="国家:null";
//                                    str[2]="时间:null";
//                                    str[3]="天气:null";
//                                    str[4]="Tmp:null";
//                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,str);
//                                    listview.setAdapter(arrayAdapter);
//                                }
//                            }
//                        });
//                    }
//                });
//
//
//
//            }
//        });
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
