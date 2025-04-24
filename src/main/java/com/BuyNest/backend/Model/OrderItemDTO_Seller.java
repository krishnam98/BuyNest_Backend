package com.BuyNest.backend.Model;

import lombok.Data;

import java.util.Date;

@Data
public class OrderItemDTO_Seller {
    private Long OrderID;
    private ProductDTO productDTO;
    private int quantity;
    private Date date;
    private Address address;
}
