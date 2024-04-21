package com.example1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example1.activity.util.ToastUtil;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    //声明控件
    //mBtnLogin---my控件叫Login(好记)
    private Button mBtnLogin;
    private EditText mEtUser;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        //找到控件
        //R.id.控件名称---Read我取得id名称为（需要的控件名称）
        mBtnLogin = findViewById(R.id.btn_login);
        mEtUser = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);

//        //实现直接跳转
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = null;
//                intent = new Intent(FirstActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });

        //匹配对应的用户名和密码才能进行登陆操作
        mBtnLogin.setOnClickListener(this);
    }

    public void onClick(View v){
        //需要获取输入的用户名和密码
        //getText()---找到控件信息，toString()转化成字符串形式
        String usersname = mEtUser.getText().toString();
        String password = mEtPassword.getText().toString();
        //弹出的内容设置
        String ok = "登陆成功！";
        String fail = "密码或账号有误，请重新输入！";
        Intent intent = null;

        //假设正确的账号密码分别是2028424002,123456
        if(usersname.equals("1")&&(password.equals("1"))){
            //正确则跳转

            //封装好的类
            ToastUtil.showMsg(FirstActivity.this,ok);
            intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            //不正确则提示登陆失败toast
            ToastUtil.showMsg(FirstActivity.this,fail);
        }

    }

}