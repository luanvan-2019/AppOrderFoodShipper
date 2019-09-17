package com.hcmunre.apporderfoodshipper.models;

import com.hcmunre.apporderfoodshipper.models.Database.DataConnetion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignInModel {
    Connection con;
    DataConnetion dataCon = new DataConnetion();
    String z = "";
//    boolean isSuccess=false;
    public String DangNhap(Shipper shipper) {
        try {
            con = dataCon.connectionData();
            if (con == null) {
                z = "Vui lòng kiểm tra kết nối";
            } else {
                String query = "Select Email,Pasword from tb_NguoiDung where Email='" + shipper.getEmail().toString() + "'and Pasword='" + shipper.getPassword().toString() + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    z = "success";
                    con.close();
                } else {
                    z = "Không đúng tên đăng nhập hoặc mật khẩu";
                }
            }
        } catch (Exception e) {
            z = "Error";
        }
        return z;
    }

}
