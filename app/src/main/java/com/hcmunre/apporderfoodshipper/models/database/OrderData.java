package com.hcmunre.apporderfoodshipper.models.database;

import com.hcmunre.apporderfoodshipper.models.entity.Order;
import com.hcmunre.apporderfoodshipper.models.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderData {
    Connection con;
    DataConnetion dataCon=new DataConnetion();
    public ArrayList<OrderDetail> getOrderDetail(int orderId){
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        try {
            String sql="Exec Sp_SelectOrderDetailByRestaurant '"+orderId+"'";
            con=dataCon.connectionData();
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            OrderDetail orderDetail;
            while(rs.next()){
                orderDetail=new OrderDetail();
                orderDetail.setOrderId(rs.getInt("OrderId"));
                orderDetail.setUserId(rs.getInt("UserId"));
                orderDetail.setFoodId(rs.getInt("FoodId"));
                orderDetail.setName(rs.getString("Name"));
                orderDetail.setQuantity(rs.getInt("Quantity"));
                orderDetail.setPrice(rs.getFloat("Price"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
    public boolean updateOrder(Order order){
        boolean success=false;
        try {
            String sql="Exec Sp_UpdateOrder '"+order.getOrderId()+"'," +
                    "'"+order.getOrderStatus()+"'";
            con=dataCon.connectionData();
            PreparedStatement pst=con.prepareStatement(sql);
            if(pst.executeUpdate()>0){
                con.close();
                success=true;
            }else{
                con.close();
                success=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public ArrayList<Order> getAllOrder(int restaurantId,int orderStatus,int startIndex,int endIndex){
        ArrayList<Order> orders=new ArrayList<>();
        try {
            String sql="Exec Sp_SelectOrderByRestaurant '"+restaurantId+"','"+orderStatus+"','"+startIndex+"'," +
                    "'"+endIndex+"'";
            con=dataCon.connectionData();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            Order order;
            while(rs.next()){
                order=new Order();
                order.setOrderId(rs.getInt("OrderId"));
                order.setUserId(rs.getInt("UserId"));
                order.setRestaurantId(rs.getInt("RestaurantId"));
                order.setOrderName(rs.getString("OrderName"));
                order.setOrderPhone(rs.getString("OrderPhone"));
                order.setOrderAddress(rs.getString("OrderAddress"));
                order.setOrderStatus(rs.getInt("OrderStatus"));
                order.setOrderDate(rs.getDate("OrderDate"));
                order.setTotalPrice(rs.getFloat("TotalPrice"));
                order.setNumberOfItem(rs.getInt("NumberOfItem"));
                order.setPayment(rs.getInt("Payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

}
