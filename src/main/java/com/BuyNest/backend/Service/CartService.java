package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.*;
import com.BuyNest.backend.Model.*;
import com.BuyNest.backend.Repository.CartItemRepo;
import com.BuyNest.backend.Repository.CartRepo;
import com.BuyNest.backend.Repository.ProductRepo;
import com.BuyNest.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public void addToCart(Long productId, String username) {
        Users user = userRepo.findByUsername(username);
        Cart cart= cartRepo.findByUser(user).orElseGet(()->{
            Cart newCart= new Cart();
            newCart.setUser(user);
            return cartRepo.save(newCart);
        });

        Product product= productRepo.findById(productId).orElse(null);
        if(product!=null){
            Optional <CartItem> cartItem= cart.getCartItems().stream()
            .filter(item-> item.getProducts().getId().equals(productId)).findFirst();

            if(cartItem.isPresent()){
                CartItem item= cartItem.get();
                item.setQuantity(item.getQuantity()+1);
                cartItemRepo.save(item);

            }
            else{
                CartItem item= new CartItem();
                item.setProducts(product);
                item.setQuantity(1);
                item.setCart(cart);
                cart.getCartItems().add(item);
                cartItemRepo.save(item);
            }
            cartRepo.save(cart);
        }



    }

    public ResponseEntity<?> getCartItems(String username) {
        Users user= userRepo.findByUsername(username);
        Cart cart= cartRepo.findByUser(user).orElse(null);
        if(cart!=null){
            List<CartItemDTO> response= cart.getCartItems().stream().map(item->{
                CartItemDTO itemDTO= new CartItemDTO();
                itemDTO.setName(item.getProducts().getName());
                itemDTO.setId(item.getProducts().getId());
                itemDTO.setDescription(item.getProducts().getDescription());
                itemDTO.setBrand(item.getProducts().getBrand());
                itemDTO.setPrice(item.getProducts().getPrice());
                itemDTO.setCategory(item.getProducts().getCategory());
                itemDTO.setReleaseDate(item.getProducts().getReleaseDate());
                itemDTO.setAvailable(item.getProducts().getAvailable());
                itemDTO.setRating(item.getProducts().getRating());
                itemDTO.setImageName(item.getProducts().getImageName());
                itemDTO.setImageType(item.getProducts().getImageType());
                itemDTO.setImageData(item.getProducts().getImageData());
                if (item.getProducts().getSeller()!= null) {
                    itemDTO.setSellerName(item.getProducts().getSeller().getUsername()); // Or getFullName(), whatever you have
                }
                itemDTO.setQuantity(item.getQuantity());
                return itemDTO;

            }).toList();
            return  ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(null);

    }
}
