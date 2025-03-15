/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Voucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class VoucherDAO {

    Connection conn = DBConnection.getConnection();

    public List<Voucher> getAllVoucher(int Account_id) {
        List<Voucher> list = new ArrayList<>();
        String sql = "SELECT Account.account_id, Vouchers.voucher_id, Vouchers.voucher_code, Vouchers.voucher_discount, Vouchers.voucher_valid_to\n"
                + "FROM     Account INNER JOIN\n"
                + "                  Vouchers ON Account.voucher_id = Vouchers.voucher_id where Account.account_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Account_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(rs.getInt("account_id"), rs.getString("voucher_code"), rs.getDouble("voucher_discount"), rs.getInt("voucher_id"));
                list.add(v);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public int getVoucherIdByAccountId(String voucher_code) {
        int id = -1;
        try {
            String sql = "SELECT voucher_id FROM Vouchers WHERE voucher_code = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, voucher_code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("voucher_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
