package com.hcmunre.apporderfoodshipper.models.database;

import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.entity.Shipper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignInData {
    Connection con;
    DataConnetion dataCon = new DataConnetion();
    String z = "";
    public String loginShipper(Shipper shipper) {
        try {
            con = dataCon.connectionData();
            if (con == null) {
                z = "Vui lòng kiểm tra kết nối";
            } else {
                String sql = "Exec Sp_SelectShipperLogin '" + shipper.getEmail().toString() + "','" + shipper.getPassword().toString() + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    Common.currentShipper =new Shipper();
                    Common.currentShipper.setId(rs.getInt("Id"));
                    Common.currentShipper.setName(rs.getString("Name"));
                    Common.currentShipper.setAddress(rs.getString("Address"));
                    Common.currentShipper.setPhone(rs.getString("Phone"));
                    Common.currentShipper.setLat(rs.getFloat("Lat"));
                    Common.currentShipper.setLng(rs.getFloat("Lng"));
                    Common.currentShipper.setImage(rs.getString("Image"));
                    Common.currentShipper.setEmail(rs.getString("Email"));
                    Common.currentShipper.setPassword(rs.getString("Password"));
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
