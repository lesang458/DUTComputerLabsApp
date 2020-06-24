package com.example.dutcomputerlabs_app.ui.UserDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dutcomputerlabs_app.models.Faculty;
import com.example.dutcomputerlabs_app.models.UserForDetailed;
import com.example.dutcomputerlabs_app.R;
import com.example.dutcomputerlabs_app.models.UserForInsert;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.example.dutcomputerlabs_app.network.services.UserService;
import com.example.dutcomputerlabs_app.utils.DialogUtils;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailFragment extends Fragment {

    private static final int GALLERY_CODE = 123;

    private UserForDetailed userDetail;
    private UserService userService;
    private int id;
    private String token;
    private Map<String,Integer> map;
    private List<String> list_genders, list_faculties;
    private ArrayAdapter<String> spinner_gender_adapter,spinner_faculty_adapter;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int day,month,year;
    private SimpleDateFormat dateFormat;

    private EditText name, phone_number, email, address;
    private Spinner spinner_gender, spinner_faculty;
    private TextView gender , faculty, birthday;
    private Button btn_back, btn_change_info, btn_save_change;
    private ImageButton btn_date_picker;
    private ImageView imageView;

    private boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_detail,container,false);
        name = root.findViewById(R.id.name);
        birthday = root.findViewById(R.id.birthday);
        gender = root.findViewById(R.id.gender);
        spinner_gender = root.findViewById(R.id.spinner_gender);
        faculty = root.findViewById(R.id.faculty);
        spinner_faculty = root.findViewById(R.id.spinner_faculty);
        phone_number = root.findViewById(R.id.phone_number);
        email = root.findViewById(R.id.email);
        address = root.findViewById(R.id.address);
        btn_back = root.findViewById(R.id.btn_back);
        btn_change_info = root.findViewById(R.id.btn_change_info);
        btn_save_change = root.findViewById(R.id.btn_save_change_info);
        btn_date_picker = root.findViewById(R.id.btn_date_picker);
        imageView = root.findViewById(R.id.image_avatar);

        return  root;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        list_genders = new ArrayList<>();
        list_genders.add("Nam");
        list_genders.add("Nữ");

        SharedPreferences pref = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        id = pref.getInt("id",1);
        token = pref.getString("token","");

        userService = ApiUtils.getUserService();

        list_faculties = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUser();
        spinner_gender_adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_item,list_genders);
        spinner_gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(spinner_gender_adapter);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender.setText(spinner_gender.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Date date = dateFormat.parse(year+"-"+(month+1)+"-"+dayOfMonth);
                            birthday.setText(dateFormat.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btn_change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.getFaculties(token).enqueue(new Callback<List<Faculty>>() {
                    @Override
                    public void onResponse(Call<List<Faculty>> call, Response<List<Faculty>> response) {
                        if(response.isSuccessful()) {
                            List<Faculty> list = response.body();
                            map = new HashMap<>();
                            for(Faculty faculty : list) {
                                map.put(faculty.getName(),faculty.getId());
                            }
                            list_faculties.clear();
                            list_faculties.addAll(map.keySet());
                            setSpinner_faculty_adapter();
                            editInfo();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Faculty>> call, Throwable t) {
                        DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",getActivity());
                    }
                });
            }
        });

        btn_save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("PREF",Context.MODE_PRIVATE);
                String name_update = name.getText().toString().trim();
                Date birthday_update = new Date();
                try {
                    birthday_update = dateFormat.parse(birthday.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean gender_update;
                if(gender.getText().toString().equals("Nam")) {
                    gender_update = false;
                }else {
                    gender_update = true;
                }
                Faculty faculty_update = new Faculty(map.get(faculty.getText().toString()),faculty.getText().toString());
                String phone_number_update = phone_number.getText().toString().trim();
                String email_update = email.getText().toString().trim();
                String address_update = address.getText().toString().trim();
                String pass_word = pref.getString("password","");
                if(name_update.equals("") || phone_number_update.equals("") || email_update.equals("") || address_update.equals("")) {
                    DialogUtils.showDialog("Vui lòng nhập đủ thông tin.","Thông báo",getActivity());
                }
                if(!name_update.equals("") && !phone_number_update.equals("") && !email_update.equals("") && !address_update.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông báo")
                            .setMessage("Đang cập nhật....")
                            .setCancelable(false);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    String imageString = null;
                    if(check){
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);

                        byte[] imageBytes = stream.toByteArray();
                        imageString = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    }
                    UserForInsert userForInsert = new UserForInsert(name_update,imageString,birthday_update,gender_update,faculty_update,phone_number_update,email_update,address_update,userDetail.getUsername(),pass_word,userDetail.getRole());
                    userService.updateUserInfo(token,id,userForInsert).enqueue(new Callback<UserForDetailed>() {
                        @Override
                        public void onResponse(Call<UserForDetailed> call, Response<UserForDetailed> response) {
                            if(response.isSuccessful()){
                                userDetail = response.body();
                                alertDialog.dismiss();
                                DialogUtils.showDialog("Bạn đã cập nhật thành công","Thông báo",getActivity());
                                showInfo();
                            }else {
                                alertDialog.dismiss();
                                DialogUtils.showDialog(response.toString(),"Lỗi",getActivity());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserForDetailed> call, Throwable t) {
                            alertDialog.dismiss();
                            DialogUtils.showDialog("Không thể kết nối đến máy. Kiểm tra kết nối internet.","Lỗi",getActivity());
                        }
                    });
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn 1 bức hình"), GALLERY_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            CropImage.activity(data.getData()).setAspectRatio(1,1).start(getContext(),this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri imageUri = CropImage.getActivityResult(data).getUri();
                imageView.setImageURI(imageUri);
                check = true;
            }
        }
    }

    private void showInfo(){
        if(userDetail != null) {
            if(userDetail.getPhotoUrl() != null){
                Glide.with(getContext())
                        .load(userDetail.getPhotoUrl())
                        .error(getActivity().getDrawable(R.drawable.user_detail_logo))
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.user_detail_logo);
            }

            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            ImageView image = header.findViewById(R.id.imageView);
            if(image.getDrawable() != imageView.getDrawable()){
                image.setImageDrawable(imageView.getDrawable());
            }
            imageView.setEnabled(false);

            name.setEnabled(false);
            name.setText(userDetail.getName());
            birthday.setText(dateFormat.format(userDetail.getBirthday()));
            spinner_gender.setSelection(getIndex(spinner_gender, userDetail.getGender()));
            faculty.setText(userDetail.getFaculty());
            phone_number.setEnabled(false);
            phone_number.setText(userDetail.getPhoneNumber());
            email.setEnabled(false);
            email.setText(userDetail.getEmail());
            address.setEnabled(false);
            address.setText(userDetail.getAddress());
        }
        btn_date_picker.setVisibility(View.INVISIBLE);
        spinner_gender.setVisibility(View.INVISIBLE);
        spinner_faculty.setVisibility(View.INVISIBLE);
        btn_back.setVisibility(View.INVISIBLE);
        btn_change_info.setVisibility(View.VISIBLE);
        btn_save_change.setVisibility(View.INVISIBLE);
    }

    private void editInfo(){
        spinner_faculty.setSelection(getIndex(spinner_faculty, userDetail.getFaculty()));
        imageView.setEnabled(true);
        name.setEnabled(true);
        btn_date_picker.setVisibility(View.VISIBLE);
        spinner_gender.setVisibility(View.VISIBLE);
        spinner_faculty.setVisibility(View.VISIBLE);
        phone_number.setEnabled(true);
        email.setEnabled(true);
        address.setEnabled(true);
        btn_back.setVisibility(View.VISIBLE);
        btn_change_info.setVisibility(View.INVISIBLE);
        btn_save_change.setVisibility(View.VISIBLE);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void getUser(){
        userService.getUser(token,id).enqueue(new Callback<UserForDetailed>() {
            @Override
            public void onResponse(Call<UserForDetailed> call, Response<UserForDetailed> response) {
                if(response.isSuccessful()){
                    userDetail = response.body();
                    showInfo();
                }
            }

            @Override
            public void onFailure(Call<UserForDetailed> call, Throwable t) {
                DialogUtils.showDialog("Không thể kết nối đến máy chủ. Kiểm tra kết nối internet.","Lỗi",getActivity());
            }
        });
    }

    private void setSpinner_faculty_adapter(){
        spinner_faculty_adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,list_faculties);
        spinner_faculty_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_faculty.setAdapter(spinner_faculty_adapter);
        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faculty.setText(spinner_faculty.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
