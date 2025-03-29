/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class UserVoucherDAO {

    Connection conn = DBConnection.getConnection();

    public List<Integer> getVoucherIdByAccId(int accountId) {
            List<Integer> voucherIds = new ArrayList<>();
        try {
            String sql = "select voucher_id from UserVouchers  where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                voucherIds.add(rs.getInt("voucher_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voucherIds;
    }
}
