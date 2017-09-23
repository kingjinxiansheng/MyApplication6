package com.example.apple.myapplication;

import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by KING on 2017/7/31 15:45
 * 邮箱:992767879@qq.com
 */

public class Model {



    public void register(EventHandler eventHandler){
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

        //在onResume方法里面注册handler
        SMSSDK.registerEventHandler(eventHandler);
        //在onResume方法里面获取国家代码
        SMSSDK.getSupportedCountries();

    }
}
