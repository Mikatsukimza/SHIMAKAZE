package com.example.afinal.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afinal.R;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = findViewById(R.id.username_register);
        final EditText etPass = findViewById(R.id.password_register);


        final Button register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(etName.getText());
                String pass = String.valueOf(etPass.getText());
                SharedPreferences sp = registerActivity.this.getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                //尝试获取密码，如果未注册过，则返回*
                String originPass = sp.getString(name,"*");
                //如果未注册过
                if (originPass == "*"){
                    editor.putString(name,pass);
                    editor.apply();
                    finish();
                    Toast.makeText(registerActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(registerActivity.this,"已被注册",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
