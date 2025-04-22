package com.BuyNest.backend.Model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String houseNo;
    private String lineOne;
    private String linetwo;
    private int pincode;

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLine1(String lineOne) {
        this.lineOne = this.lineOne;
    }

    public String getLinetwo() {
        return linetwo;
    }

    public void setLinetwo(String linetwo) {
        this.linetwo = linetwo;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
