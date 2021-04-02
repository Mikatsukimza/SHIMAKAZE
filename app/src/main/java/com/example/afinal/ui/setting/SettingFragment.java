package com.example.afinal.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.afinal.R;

import com.example.afinal.ui.login.LoginActivity;


public class SettingFragment extends Fragment {
    TextView userName ;
    SharedPreferences sp ;
    SharedPreferences.Editor editor;
    CheckBox remPass;
    CheckBox auLogin;
    private SettingViewModel settingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        sp = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();

        userName = root.findViewById(R.id.setting_name);
        userName.setText(sp.getString("currentUser", "*"));

        TextView userId = root.findViewById(R.id.setting_id);
        long l = System.currentTimeMillis();
        int i = (int)( l % 100000 );
        String str = "ID:"+Integer.toString(i) ;
        userId.setText(str);

        final LinearLayout changepass = root.findViewById(R.id.setting_changePass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),changePass.class);
                startActivity(intent);
            }
        });

        Button btLogout = root.findViewById(R.id.setting_Logout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("automatic_login",false);
                editor.apply();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });




        //记住密码cb
        remPass = getActivity().findViewById(R.id.setting_rememberPass_cb);

        //是否记住密码
        boolean remember_password =  sp.getBoolean("remember_password",false);

        //自动登陆cb
        auLogin = getActivity().findViewById(R.id.setting_auLogin_cb);
        //是否自动登陆
        boolean automatic_login = sp.getBoolean("automatic_login",false);


        //如果记住密码
        if(remember_password){
            //remPass.setChecked(true);
            //如果自动登陆
//            if (automatic_login ){
//                //设置选中
//                auLogin.setChecked(true);
//            }

        }
//        //自动登陆选中时，记住密码同时选中
//        auLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (auLogin.isChecked()){
//                    remPass.setChecked(true);
//                    /******************************************************需要进行操作*/
//                }
//            }
//        });
//        //记住密码取消时，自动登录同时取消
//        remPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (auLogin.isChecked() && !remPass.isChecked()){
//                    auLogin.setChecked(false);
//                    /******************************************************需要进行操作*/
//                }
//            }
//        });




        return root;
    }
}