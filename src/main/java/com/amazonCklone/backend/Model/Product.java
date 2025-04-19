package com.amazonCklone.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private Boolean available;
    private int quantity;
    private int rating;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] imageData;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users seller;


}
