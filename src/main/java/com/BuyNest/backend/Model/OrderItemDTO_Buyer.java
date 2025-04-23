package com.BuyNest.backend.Model;

import lombok.Data;

@Data
public class OrderItemDTO_Buyer {
    private ProductDTO productDTO;
    private int quantity;

}
