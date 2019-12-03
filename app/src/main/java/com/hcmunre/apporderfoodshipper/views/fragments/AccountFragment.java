package com.hcmunre.apporderfoodshipper.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.entity.Shipper;
import com.hcmunre.apporderfoodshipper.views.activities.OrderHistoryActivity;
import com.hcmunre.apporderfoodshipper.views.activities.PreferenceUtilsShipper;
import com.hcmunre.apporderfoodshipper.views.activities.SignInActivity;
import com.hcmunre.apporderfoodshipper.views.activities.UserInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.linear_order_history)
    LinearLayout linear_order_history;
    @BindView(R.id.btn_sign_out)
    TextView btn_sign_out;
    @BindView(R.id.txt_name_user)
    TextView txt_name_user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_account,container,false);
      unbinder= ButterKnife.bind(this,view);
      eventClick();
      signOut();
      return view;
    }
    private void eventClick() {
        txt_name_user.setText(PreferenceUtilsShipper.getName(getActivity()));
        txt_name_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });
        linear_order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
            }
        });

    }
    private void signOut() {
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder comfirmSignOut = new AlertDialog.Builder(v.getContext())
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có muốn đăng xuất không ?")
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferenceUtilsShipper.savePassword(null, getActivity());
                                PreferenceUtilsShipper.saveEmail(null, getActivity());
                                Intent intent = new Intent(getActivity(), SignInActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                comfirmSignOut.show();
            }
        });
    }
}
