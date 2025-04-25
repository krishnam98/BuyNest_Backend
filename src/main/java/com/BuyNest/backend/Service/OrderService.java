package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.*;
import com.BuyNest.backend.Repository.CartRepo;
import com.BuyNest.backend.Repository.OrderItemRepo;
import com.BuyNest.backend.Repository.OrderRepo;
import com.BuyNest.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderRepo orderRepo;

    public ResponseEntity<?> createOrder(Address address, Principal principal) {
        Users user= userRepo.findByUsername(principal.getName());
        Cart cart = cartRepo.findByUser(user).orElse(null);
        if (cart!=null){
            Orders orders =new Orders();
            orders.setBuyer(user);
            BigDecimal price= BigDecimal.ZERO;
            cart.getCartItems().forEach(item -> {
                OrderItem orderItem= new OrderItem();
                orderItem.setProduct(item.getProducts());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSeller(item.getProducts().getSeller());
                orderItem.setOrders(orders);
                System.out.println(orderItem);
                orders.getOrderItems().add(orderItem);
            });
            for(int i = 0; i< cart.getCartItems().size(); i++){
                price= price.add(cart.getCartItems().get(i).getProducts().getPrice().multiply(BigDecimal.valueOf(cart.getCartItems().get(i).getQuantity())));
            }
            orders.setPrice(price);
            orders.setAddress(address);
            orders.setDateOfCreation(new Date());
            orderRepo.save(orders);
            return new ResponseEntity<>("Order Created successfully",HttpStatus.CREATED);

        }else{
            System.out.println("No cart present");
            return new ResponseEntity<>("No cart present!", HttpStatus.BAD_REQUEST);
        }

    }

    public ProductDTO convertToDTO(Product product){
        ProductDTO dto= new ProductDTO();
        dto.setName(product.getName());
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setReleaseDate(product.getReleaseDate());
        dto.setAvailable(product.getAvailable());
        dto.setQuantity(product.getQuantity());
        dto.setRating(product.getRating());
        dto.setImageName(product.getImageName());
        dto.setImageType(product.getImageType());
        dto.setImageData(product.getImageData());

        // Set seller's name safely
        if (product.getSeller() != null) {
            dto.setSellerName(product.getSeller().getUsername());
        }
        return dto;
    }

    public OrderDTO_Buyer createOrderDto(Orders order){
        OrderDTO_Buyer dto= new OrderDTO_Buyer();
        dto.setAddress(order.getAddress());
        dto.setPrice(order.getPrice());
        dto.setDateOfCreation(order.getDateOfCreation());
        dto.setOrderID(order.getId());
        order.getOrderItems().stream().forEach( item->{
                    OrderItemDTO_Buyer orderitemDto_buyer= new OrderItemDTO_Buyer();
                    orderitemDto_buyer.setQuantity(item.getQuantity());
                    orderitemDto_buyer.setProductDTO(convertToDTO(item.getProduct()));
                    dto.getOrderItemDTO_buyerList().add(orderitemDto_buyer);
                });

        return dto;

    }
    public ResponseEntity<?> getOrders(Principal principal){
        Users user = userRepo.findByUsername(principal.getName());
        List <Orders> orders=orderRepo.findByBuyer(user);
       if(orders!=null){
           List <OrderDTO_Buyer> response= new ArrayList<>();
           orders.stream().forEach(item->{
               response.add(createOrderDto(item));
           });
           return new ResponseEntity<>(response,HttpStatus.OK);
       }
       else{
           return  new ResponseEntity<>("null",HttpStatus.NOT_FOUND);
       }

    }

    public OrderItemDTO_Seller createDTOForSeller(OrderItem orderItem){
        OrderItemDTO_Seller dto_seller= new OrderItemDTO_Seller();
        dto_seller.setProductDTO(convertToDTO(orderItem.getProduct()));
        dto_seller.setDate(orderItem.getOrders().getDateOfCreation());
        dto_seller.setQuantity(orderItem.getQuantity());
        dto_seller.setAddress(orderItem.getOrders().getAddress());
        dto_seller.setOrderID(orderItem.getOrders().getId());
        return dto_seller;
    }


    public ResponseEntity<?> getSellerOrders(Principal principal) {
        Users user= userRepo.findByUsername(principal.getName());
        List<OrderItem> orderItemList= orderItemRepo.findBySeller(user);
        if(orderItemList==null){
            return new ResponseEntity<>("No Orders Yet!",HttpStatus.OK);
        }

        List<OrderItemDTO_Seller> response= new ArrayList<>();
        orderItemList.stream().forEach(item->{
            response.add(createDTOForSeller(item));
        });
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
