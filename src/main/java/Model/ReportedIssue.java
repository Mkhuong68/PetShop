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
public class ReportedIssue {
    private int issueId;
    private int accountId;
    private String issueDescription;
    private Date reportedDate;
    private boolean isResolved;

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public boolean isIsResolved() {
        return isResolved;
    }

    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public ReportedIssue() {}

    public ReportedIssue(int issueId, int accountId, String issueDescription, Date reportedDate, boolean isResolved) {
        this.issueId = issueId;
        this.accountId = accountId;
        this.issueDescription = issueDescription;
        this.reportedDate = reportedDate;
        this.isResolved = isResolved;
    }
}
