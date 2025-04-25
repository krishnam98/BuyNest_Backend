package com.BuyNest.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Data
public class OrderDTO_Buyer {
    private Long orderID;
    private Address address;
    private BigDecimal price;
    private Date dateOfCreation;

    List <OrderItemDTO_Buyer> orderItemDTO_buyerList= new ArrayList<>();
}
