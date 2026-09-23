package com.eczaneler.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AccountInfo {
    @SerializedName("status")
    private String status;

    @SerializedName("email")
    private String email;

    @SerializedName("active_package")
    private String activePackage;

    @SerializedName("days_left")
    private Integer daysLeft;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("allowed_ips")
    private List<String> allowedIps;

    @SerializedName("rate_limit")
    private String rateLimit;

    public AccountInfo() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getActivePackage() { return activePackage; }
    public void setActivePackage(String activePackage) { this.activePackage = activePackage; }

    public Integer getDaysLeft() { return daysLeft; }
    public void setDaysLeft(Integer daysLeft) { this.daysLeft = daysLeft; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public List<String> getAllowedIps() { return allowedIps; }
    public void setAllowedIps(List<String> allowedIps) { this.allowedIps = allowedIps; }

    public String getRateLimit() { return rateLimit; }
    public void setRateLimit(String rateLimit) { this.rateLimit = rateLimit; }
}
