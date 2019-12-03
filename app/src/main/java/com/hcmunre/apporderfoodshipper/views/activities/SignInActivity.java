package com.hcmunre.apporderfoodshipper.views.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.database.ShipperData;
import com.hcmunre.apporderfoodshipper.models.entity.Shipper;
import com.hcmunre.apporderfoodshipper.views.activities.HomeActivity;
import com.hcmunre.apporderfoodshipper.views.activities.PreferenceUtilsShipper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.btn_signin_shipper)
    Button btn_signin_shipper;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editPass)
    EditText editPass;
    String usernam, passwordd;
    Shipper shipperLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        eventClick();
    }
    private void eventClick(){
        btn_signin_shipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernam = editEmail.getText().toString();
                passwordd = editPass.getText().toString();
                new CheckLoginShiper(SignInActivity.this).execute();
            }
        });
        PreferenceUtilsShipper utils = new PreferenceUtilsShipper();

        if (utils.getEmail(this) != null) {
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
        }
    }
    public class CheckLoginShiper extends AsyncTask<String, String, Shipper> {

        private ProgressDialog mDialog;
        private Context mContext = null;

        public CheckLoginShiper(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Đang đăng nhập ...");
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Shipper shipper) {
            mDialog.dismiss();
            if (usernam.trim().equals("") || passwordd.trim().equals("")) {
                Toast.makeText(mContext, "Vui lòng nhập tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            } else if (shipper != null) {
                Common.currentShipper = shipper;
                PreferenceUtilsShipper.saveEmail(usernam, SignInActivity.this);
                PreferenceUtilsShipper.savePassword(passwordd, SignInActivity.this);
                PreferenceUtilsShipper.saveUserId(Common.currentShipper.getId(), SignInActivity.this);
                PreferenceUtilsShipper.saveName(Common.currentShipper.getName(), SignInActivity.this);
                PreferenceUtilsShipper.savePhone(Common.currentShipper.getPhone(), SignInActivity.this);
                PreferenceUtilsShipper.saveAddress(Common.currentShipper.getAddress(), SignInActivity.this);
                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(mContext, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }

        //đây là luồng bên ngoài để xử lý logic
        @SuppressLint("WrongThread")
        @Override
        protected Shipper doInBackground(String... params)//...mảng dạng array
        {
            Shipper shipper= new Shipper();
            ShipperData shipperData = new ShipperData();
            shipperLogin = new Shipper();
            shipperLogin.setEmail(usernam);
            shipperLogin.setPassword(passwordd);
            shipper = shipperData.getInforShipper(shipperLogin);

            return shipper;
        }
    }

}
