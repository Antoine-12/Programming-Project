package com.mycompany.loginu;

import java.io.Serializable;

public class PromoCode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pCode;
    private double value;
    private String discount;
    private String cutoffDate;

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCutoffDate() {
        return cutoffDate;
    }

    public void setCutoffDate(String cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

}
