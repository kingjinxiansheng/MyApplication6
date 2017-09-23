package com.example.apple.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity {

    private EventHandler eventHandler;
    private TextView text;
    private TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(true);

        // 创建EventHandler对象
        /* public void afterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable)data;
                String msg = throwable.getMessage();
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            } else {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 处理你自己的逻辑
                    Toast.makeText(MainActivity.this, "11111111", Toast.LENGTH_SHORT).show();
                }
            }
        }*/
        //理解为一个事件的handler
        eventHandler = new EventHandler() {
            @Override//注册的时候走这个方法
            public void onRegister() {
                super.onRegister();
            }

            @Override//事件提交完成的时候走这个方法
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                if (data instanceof Throwable) {//失败
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {//SMSSDK.RESULT_COMPLETE
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(MainActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        // 处理你自己的逻辑
                        //弹一个吐司表示注册成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            Toast.makeText(MainActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }

                }
            }

            @Override//事件提交之前走这个方法
            public void beforeEvent(int i, Object o) {
                super.beforeEvent(i, o);
            }
        };




    }


    @Override
    protected void onResume() {
        super.onResume();
        //在onResume方法里面注册handler
        SMSSDK.registerEventHandler(eventHandler);
        //在onResume方法里面获取国家代码
        SMSSDK.getSupportedCountries();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在方法里面解注册handler
        SMSSDK.unregisterEventHandler(eventHandler);
    }



    private void initView() {
        text = (TextView) findViewById(R.id.text);//这里改为edittext
        text1 = (TextView) findViewById(R.id.text1);//这里改为edittext

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86", "15710062741");  //获取验证码
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.submitVerificationCode("86", "15710062741", "7859");//提交验证码
            }
        });
    }
}
