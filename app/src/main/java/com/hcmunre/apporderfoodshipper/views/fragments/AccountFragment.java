package com.hcmunre.apporderfoodshipper.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hcmunre.apporderfoodshipper.R;
import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.entity.Shipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.btnupdateuser)
    Button btnUpdateUser;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editAddress)
    EditText editAddress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_account,container,false);
      unbinder= ButterKnife.bind(this,view);
      btnUpdateUser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
                editName.setEnabled(true);
          }
      });
      getInforShipper();
      return view;
    }
    private void getInforShipper(){
        Intent intent=getActivity().getIntent();
        Shipper shipper=(Shipper)intent.getSerializableExtra(Common.KEY_SHIPPER);
        editName.setText(shipper.getName());
        editAddress.setText(shipper.getAddress());
        editEmail.setText(shipper.getEmail());
    }
}
