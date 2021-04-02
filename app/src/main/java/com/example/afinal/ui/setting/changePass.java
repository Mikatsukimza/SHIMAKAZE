package com.example.afinal.ui.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afinal.R;

public class changePass extends AppCompatActivity {
    EditText etOldPass;
    EditText etNewPass;
    EditText etNewPass2;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        etOldPass = findViewById(R.id.changePass_oldPass);
        etNewPass = findViewById(R.id.changePass_newPass);
        etNewPass2 = findViewById(R.id.changePass_newPass2);
        sp = this.getSharedPreferences("user",MODE_PRIVATE);
        editor = sp.edit();
        Button btChange = findViewById(R.id.btChangePass);
        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(changePass.this);
                String oldPass = String.valueOf(etOldPass.getText());
                String newPass = String.valueOf(etNewPass.getText());
                String newPass2 = String.valueOf(etNewPass2.getText());
                String currentUser = sp.getString("currentUser","*");
                if(  !sp.getString( currentUser,"*").equals(oldPass)   ){
                    toast.makeText(changePass.this,"旧密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if(!newPass.equals(newPass2)){
                    toast.makeText(changePass.this,"新密码两次不一致，请重新输入",Toast.LENGTH_SHORT).show();;
                }
                else if(newPass.equals(oldPass)){
                    toast.makeText(changePass.this,"新密码不能和旧密码相同",Toast.LENGTH_SHORT).show();;
                }
                else {
                    editor.putString(currentUser,newPass);
                    editor.apply();
                    editor.commit();
                    toast.makeText(changePass.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });


    }
}
