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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.database.SignInData;
import com.hcmunre.apporderfoodshipper.models.entity.Shipper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.btnSignin)
    Button btnSigin;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editPass)
    EditText editPass;
    @BindView(R.id.txtSignup)
    TextView txtSignup;
    @BindView(R.id.txtForgetPass)
    TextView txtForgetPass;
    @BindView(R.id.progress_login)
    ProgressBar progress_login;
    String usernam, passwordd;
    Shipper shipper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        progress_login.setVisibility(View.GONE);
        btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernam = editEmail.getText().toString();
                passwordd = editPass.getText().toString();
                new CheckLogin(SignInActivity.this).execute();
            }
        });
        listenClickSignup();
        listenClickForgetPass();
    }
    private void listenClickSignup() {
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    private void listenClickForgetPass() {
        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });
    }
    public class CheckLogin extends AsyncTask<String, String, String>
    {

        private ProgressDialog mDialog;
        private Context mContext = null;

        public CheckLogin(Context context) {
            mContext = context;
        }

        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Đang đăng nhập ...");
            mDialog.show();
        }

        @Override
        protected void onPostExecute(String r) {
            mDialog.dismiss();
            if (z != "success") {
                Toast.makeText(SignInActivity.this, r, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                intent.putExtra(Common.KEY_SHIPPER, Common.currentshipper);
                startActivity(intent);
                finish();
            }
        }

        //đây là luồng bên ngoài để xử lý logic
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params)//...mảng dạng array
        {

            if (usernam.trim().equals("") || passwordd.trim().equals(""))
                z = "Vui lòng nhập tên đăng nhập hoặc mật khẩu";
            else {
                SignInData userModel = new SignInData();
                shipper= new Shipper();
                shipper.setEmail(usernam);
                shipper.setPassword(passwordd);
                z = userModel.loginShipper(shipper);
            }
            return z;
        }
    }
}
