
package com.mycompany.loginu;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String St;
    private String Av;
    private String Zn;
    
   private String WholeAddress;

    public String getSt() {
        return St;
    }

    public void setSt(String St) {
        this.St = St;
    }

    public String getAv() {
        return Av;
    }

    public void setAv(String Av) {
        this.Av = Av;
    }

    public String getZn() {
        return Zn;
    }

    public void setZn(String Zn) {
        this.Zn = Zn;
    }

    public String getWholeAddress() {
        return WholeAddress;
    }

    public void setWholeAddress(String WholeAddress) {
        this.WholeAddress = WholeAddress;
    }
    
}
