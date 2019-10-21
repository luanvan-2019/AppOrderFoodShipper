package com.hcmunre.apporderfoodshipper.models.database;

import com.hcmunre.apporderfoodshipper.commons.Common;
import com.hcmunre.apporderfoodshipper.models.database.DataConnetion;
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
                String query = "Exec Sp_SelectShipperLogin '" + shipper.getEmail().toString() + "','" + shipper.getPassword().toString() + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    Common.currentshipper=new Shipper();
                    Common.currentshipper.setId(rs.getInt("Id"));
                    Common.currentshipper.setName(rs.getString("Name"));
                    Common.currentshipper.setAddress(rs.getString("Address"));
                    Common.currentshipper.setPhone(rs.getString("Phone"));
                    Common.currentshipper.setLat(rs.getFloat("Lat"));
                    Common.currentshipper.setLng(rs.getFloat("Lng"));
                    Common.currentshipper.setImage(rs.getString("Image"));
                    Common.currentshipper.setEmail(rs.getString("Email"));
                    Common.currentshipper.setPassword(rs.getString("Password"));
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
