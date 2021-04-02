package  com.example.afinal.ui.login;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.R;
import com.example.afinal.Weather;

public class LoginActivity extends AppCompatActivity {
    String inputName;
    String inputPass;
    CheckBox remPass;
    CheckBox auLogin;
    Toast toast;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = LoginActivity.this.getSharedPreferences("user",MODE_PRIVATE);
        editor = sp.edit();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        toast = new Toast(LoginActivity.this);
        editor.putString("Admin","123456");

        //记住密码cb
        remPass = findViewById(R.id.cbRember);
        //是否记住密码
        boolean remember_password =  sp.getBoolean("remember_password",false);

        //自动登陆cb
        auLogin = findViewById(R.id.cbLogin);
        //是否自动登陆
        boolean automatic_login = sp.getBoolean("automatic_login",false);


        //如果记住密码
        if(remember_password){
            usernameEditText.setText(sp.getString("account",""));
            passwordEditText.setText(sp.getString("password",""));
            remPass.setChecked(true);

            //如果自动登陆
            if (automatic_login ){
                //设置选中
                auLogin.setChecked(true);
                inputName = String.valueOf(usernameEditText.getText());
                inputPass = String.valueOf(passwordEditText.getText());
                Login(inputName,inputPass);

            }

        }

        auLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auLogin.isChecked()){
                    remPass.setChecked(true);
                }
            }
        });
        remPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auLogin.isChecked() && !remPass.isChecked()){
                    auLogin.setChecked(false);
                }
            }
        });





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName = String.valueOf(usernameEditText.getText());
                inputPass = String.valueOf(passwordEditText.getText());

                if(remPass.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("account",inputName);
                    editor.putString("password",inputPass);
                    editor.apply();
                    if(auLogin.isChecked()){
                        editor.putBoolean("automatic_login",true);
                        editor.apply();

                    }
                }
                else {
                    editor.putBoolean("remember_password",false);
                }
                if(!auLogin.isChecked()){
                    editor.putBoolean("automatic_login",false);

                }

                Login(inputName,inputPass);


            }
        });

        final Button tvRegister = findViewById(R.id.btRegester);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });
    }

    //登陆功能，检验后进行登陆，返回登陆结果
    private  boolean Login(String inputName,String inputPass){
        //尝试获取密码，如果未注册过，则返回*
        String pass = sp.getString(inputName,"*");
        //如果密码匹配，那么登陆
        if(  pass.equals(inputPass))  {
            editor.putString("currentUser",inputName);
            editor.apply();
            editor.commit();
            finish();
            startActivity(new Intent(this, Weather.class));
            toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            toast.makeText(getApplicationContext(), "账号或密码错误，请重新输入", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
