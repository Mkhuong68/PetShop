/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author tvhun
 */
public class RevenueReport {
    private int reportId;
    private Date reportDate;
    private double totalRevenue;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public RevenueReport() {}

    public RevenueReport(int reportId, Date reportDate, double totalRevenue) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.totalRevenue = totalRevenue;
    }
}
