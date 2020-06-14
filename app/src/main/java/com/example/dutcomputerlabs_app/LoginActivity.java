package com.example.dutcomputerlabs_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dutcomputerlabs_app.models.UserForLogin;
import com.example.dutcomputerlabs_app.models.UserToken;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.network.services.AuthService;
import com.example.dutcomputerlabs_app.utils.DialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private AuthService authService;
    private UserToken userToken;
    private UserForLogin user;
    private EditText username, password;
    private Button login;
    private TextView err_username, err_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        login = (Button) findViewById(R.id.btn_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        err_username = (TextView) findViewById(R.id.text_err_username);
        err_password = (TextView) findViewById(R.id.text_err_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = username.getText().toString().trim();
                String pass_word = password.getText().toString().trim();
                if(user_name.equals("")) {
                    err_username.setText("Enter your username");
                } else {
                    err_username.setText("");
                }
                if(pass_word.equals("")) {
                    err_password.setText("Enter your password");
                }else {
                    err_password.setText("");
                }
                if(!user_name.equals("") && !pass_word.equals("")) {
                    user = new UserForLogin(user_name,pass_word);
                    authService = ApiUtils.getAuthService();
                    authService.login(user).enqueue(new Callback<UserToken>() {
                        @Override
                        public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                            if(response.isSuccessful()){
                                userToken = response.body();
                                if(userToken.getUser().getRole().equals("LECTURER")) {
                                    SharedPreferences pref = getSharedPreferences("PREF",MODE_PRIVATE);
                                    pref.edit().putString("token","Bearer "+userToken.getToken()).apply();
                                    pref.edit().putString("password",pass_word).apply();
                                    pref.edit().putInt("id",userToken.getUser().getId()).apply();
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }
                                else {
                                    DialogUtils.showDialog("Tài khoản không phải là giảng viên. Không thể đăng nhập.","Thông báo",LoginActivity.this);
                                    username.setText("");
                                    password.setText("");
                                }
                            } else {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response.errorBody().string());
                                    String message = jsonObject.getString("message");
                                    if(message.equals("Người dùng không tồn tại")) {
                                        err_username.setText(message);
                                    } else err_password.setText(message);
                                    username.setText("");
                                    password.setText("");
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserToken> call, Throwable t) {
                            DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",LoginActivity.this);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
