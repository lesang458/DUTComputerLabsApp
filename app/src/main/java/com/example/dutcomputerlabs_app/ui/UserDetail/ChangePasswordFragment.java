package com.example.dutcomputerlabs_app.ui.UserDetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.PasswordToUpdate;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.network.services.UserService;
import com.example.dutcomputerlabs_app.utils.DialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private PasswordToUpdate passwordToUpdate;
    private EditText old_password, new_password, confirm_new_password;
    private TextView err_old_password, err_new_password, err_confirm_new_password;
    private Button btnSave;
    private UserService userService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_change_password,container,false);

        old_password = root.findViewById(R.id.old_password);
        new_password = root.findViewById(R.id.new_password);
        confirm_new_password = root.findViewById(R.id.confirm_new_password);
        err_old_password = root.findViewById(R.id.text_err_old_password);
        err_new_password = root.findViewById(R.id.text_err_new_password);
        err_confirm_new_password = root.findViewById(R.id.text_err_confirm_new_password);
        btnSave = root.findViewById(R.id.btn_save_change_password);

        return root;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = old_password.getText().toString().trim();
                String newPassword = new_password.getText().toString().trim();
                String confirmPassword = confirm_new_password.getText().toString().trim();

                if(oldPassword.equals("")) {
                    err_old_password.setText("Vui lòng nhập mật khẩu cũ");
                }else {
                    err_old_password.setText("");
                }
                if(newPassword.equals("")) {
                    err_new_password.setText("Vui lòng nhập mật khẩu mới");
                }else {
                    err_new_password.setText("");
                }
                if(confirmPassword.equals("")) {
                    err_confirm_new_password.setText("Vui lòng xác nhận mật khẩu mới");
                }else{
                    err_confirm_new_password.setText("");
                }
                if(!oldPassword.equals("") && !newPassword.equals("") && !confirmPassword.equals("")) {
                    if(newPassword.equals(confirmPassword)) {
                        err_confirm_new_password.setText("");
                        SharedPreferences pref = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
                        int id = pref.getInt("id",1);
                        String token = pref.getString("token","");
                        passwordToUpdate = new PasswordToUpdate(oldPassword,newPassword);
                        userService = ApiUtils.getUserService();
                        userService.changePassword(token,id,passwordToUpdate).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()) {
                                    pref.edit().putString("password",newPassword);
                                    clearText();
                                    DialogUtils.showDialog("Bạn đã đổi mật khẩu thành công.","Thông báo",getActivity());
                                }else {
                                    clearText();
                                    DialogUtils.showDialog("Mật khẩu cũ không đúng.","Thông báo",getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",getActivity());
                            }
                        });
                    }else {
                        clearText();
                        err_confirm_new_password.setText("Mật khẩu xác nhận không khớp.");
                    }
                }
            }
        });
    }

    public void clearText(){
        old_password.setText("");
        new_password.setText("");
        confirm_new_password.setText("");
    }
}
