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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class VoucherDAO {

    Connection conn = DBConnection.getConnection();

    public Integer getVoucherIdByAccId(String voucherCode) {
        Integer voucherId = null;
        String sql = "SELECT voucher_id FROM Vouchers WHERE voucher_code = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, voucherCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                voucherId = rs.getInt("voucher_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voucherId;
    }

    public double getVoucherDiscount(int voucherId) {
        double discount = 0;
        String sql = "SELECT voucher_discount FROM Vouchers WHERE voucher_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, voucherId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                discount = rs.getDouble("voucher_discount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discount;
    }

    public void addVoucher(Voucher voucher) {
        String sql = "INSERT INTO Vouchers (voucher_code, voucher_description, voucher_discount, voucher_valid_from, voucher_valid_to, voucher_status, voucher_type, is_used, is_hidden, created_date) "
                + "VALUES (?, ?, ?, GETDATE(), ?, ?, ?, 0, 0, GETDATE())";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, voucher.getVoucherCode());
            ps.setString(2, voucher.getVoucherDescription());
            ps.setInt(3, voucher.getVoucherDiscount());
            ps.setDate(4, new java.sql.Date(voucher.getVoucherValidTo().getTime()));
            ps.setBoolean(5, voucher.isVoucherStatus());
            ps.setString(6, voucher.getVoucherType());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers";
        try ( Connection conn = DBConnection.getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Voucher voucher = new Voucher();
                voucher.setVoucherId(rs.getInt("voucher_id"));
                voucher.setVoucherCode(rs.getString("voucher_code"));
                voucher.setVoucherDescription(rs.getString("voucher_description"));
                voucher.setVoucherDiscount(rs.getInt("voucher_discount"));
                voucher.setVoucherValidFrom(rs.getTimestamp("voucher_valid_from"));
                voucher.setVoucherValidTo(rs.getDate("voucher_valid_to"));
                voucher.setVoucherStatus(rs.getBoolean("voucher_status"));
                voucher.setVoucherType(rs.getString("voucher_type"));
                voucher.setUsed(rs.getBoolean("is_used"));
                voucher.setHidden(rs.getBoolean("is_hidden"));
                list.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Voucher getVoucherById(int id) {
        Voucher voucher = null;
        String sql = "SELECT * FROM Vouchers WHERE voucher_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    voucher = new Voucher();
                    voucher.setVoucherId(rs.getInt("voucher_id"));
                    voucher.setVoucherCode(rs.getString("voucher_code"));
                    voucher.setVoucherDescription(rs.getString("voucher_description"));
                    voucher.setVoucherDiscount(rs.getInt("voucher_discount"));
                    voucher.setVoucherValidFrom(rs.getTimestamp("voucher_valid_from"));
                    voucher.setVoucherValidTo(rs.getDate("voucher_valid_to"));
                    voucher.setVoucherStatus(rs.getBoolean("voucher_status"));
                    voucher.setVoucherType(rs.getString("voucher_type"));
                    voucher.setUsed(rs.getBoolean("is_used"));
                    voucher.setHidden(rs.getBoolean("is_hidden"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voucher;
    }

    public void updateVoucher(Voucher voucher) {
        String sql = "UPDATE Vouchers SET voucher_code = ?, voucher_description = ?, voucher_discount = ?, voucher_valid_to = ?, voucher_status = ?, voucher_type = ?, is_used = ?, is_hidden = ? WHERE voucher_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, voucher.getVoucherCode());
            ps.setString(2, voucher.getVoucherDescription());
            ps.setInt(3, voucher.getVoucherDiscount());
            ps.setDate(4, new java.sql.Date(voucher.getVoucherValidTo().getTime()));
            ps.setBoolean(5, voucher.isVoucherStatus());
            ps.setString(6, voucher.getVoucherType());
            ps.setBoolean(7, voucher.isUsed());
            ps.setBoolean(8, voucher.isHidden());
            ps.setInt(9, voucher.getVoucherId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Voucher> getAllVoucherAcc(int voucherId) {
        List<Voucher> list = new ArrayList<>();
        String sql = "  SELECT * FROM vouchers WHERE voucher_id = ?\n"
                + "                AND GETDATE() BETWEEN voucher_valid_from AND voucher_valid_to\n"
                + "                AND voucher_status = 'TRUE'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, voucherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(
                        rs.getInt("voucher_id"),
                        rs.getString("voucher_code"),
                        rs.getString("voucher_description"),
                        rs.getInt("voucher_discount"),
                        rs.getTimestamp("voucher_valid_from"),
                        rs.getDate("voucher_valid_to"),
                        rs.getBoolean("voucher_status"),
                        rs.getBoolean("is_used"),
                        rs.getString("voucher_type"),
                        rs.getBoolean("is_hidden")
                );
                list.add(v);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
}
