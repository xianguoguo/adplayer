package com.adshow.player.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.adshow.player.R;
import com.adshow.player.activitys.app.AppAutoRun;
import com.adshow.player.activitys.downloadProcess.DownloadProcessActivity;
import com.adshow.player.activitys.fullscreen.PlayerActivity;
import com.adshow.player.activitys.playProcess.PlayTimelineActivity;
import com.adshow.player.bean.DeviceInfo;
import com.adshow.player.bean.UserLogin;
import com.adshow.player.bean.UserToken;
import com.adshow.player.service.ADPlayerBackendService;
import com.adshow.player.service.request.LoginRequest;
import com.adshow.player.service.response.RestResult;
import com.adshow.player.util.DeviceUtil;
import com.adshow.player.util.SharedUtil;
import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    ActionProcessButton loginButton;
    EditText usernameEditText;
    EditText passwordEditText;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (ActionProcessButton) findViewById(R.id.loginButton);

        UserLogin userLogin = DeviceUtil.getUserLogin();
        if (userLogin != null) {
            usernameEditText.setText(userLogin.getUsername());
            passwordEditText.setText(userLogin.getPassword());
        }

        Call<RestResult<Object>> call = ADPlayerBackendService.getInstance().getRestApi().validateToken();
        call.enqueue(new Callback<RestResult<Object>>() {
            @Override
            public void onResponse(Call<RestResult<Object>> call, Response<RestResult<Object>> response) {
                if (response.body().isSuccess()) {
                    usernameEditText.setEnabled(false);
                    passwordEditText.setEnabled(false);
                    loginButton.setText("登出");
                    loginButton.setEnabled(true);
                    return;
                }
            }

            @Override
            public void onFailure(Call<RestResult<Object>> call, Throwable t) {
                System.out.println("accessToken 校验失败");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.equals(loginButton.getText(), "登出")) {
                    DeviceUtil.setUserToken(null);
                    usernameEditText.setEnabled(true);
                    passwordEditText.setEnabled(true);
                    loginButton.setText("登陆");
                    return;
                }

                if (TextUtils.isEmpty(usernameEditText.getText().toString()) || TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    return;
                }

                usernameEditText.setEnabled(false);
                passwordEditText.setEnabled(false);
                loginButton.setEnabled(false);
                loginButton.setMode(ActionProcessButton.Mode.ENDLESS);
                loginButton.setProgress(10);

                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            DeviceInfo deviceInfo = DeviceUtil.getDeviceInfo();
                            Call<RestResult<Object>> call = ADPlayerBackendService.getInstance().getRestApi().login(
                                    deviceInfo.getId(), deviceInfo.getIp(), deviceInfo.getMac(), deviceInfo.getResolution(), deviceInfo.getVersion(),
                                    usernameEditText.getText().toString(), passwordEditText.getText().toString());
                            call.enqueue(new Callback<RestResult<Object>>() {
                                @Override
                                public void onResponse(Call<RestResult<Object>> call, Response<RestResult<Object>> response) {
                                    loginButton.setProgress(50);
                                    RestResult<Object> restResponse = response.body();
                                    System.out.println(restResponse);
                                    UserToken userToken = new UserToken();
                                    userToken.setAccessToken(restResponse.getResult().toString());
                                    userToken.setExpiresIn(new Date(System.currentTimeMillis() + 15 * 60 * 60 * 24 * 1000));
                                    DeviceUtil.setUserToken(userToken);

                                    UserLogin userLogin = new UserLogin();
                                    userLogin.setUsername(usernameEditText.getText().toString());
                                    userLogin.setPassword(passwordEditText.getText().toString());
                                    DeviceUtil.setUserLogin(userLogin);

                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(1000);
                                                Intent intent = new Intent(context, MainActivity.class);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.run();
                                }

                                @Override
                                public void onFailure(Call<RestResult<Object>> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.run();

            }
        });
    }
}
