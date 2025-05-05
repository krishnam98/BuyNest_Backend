package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.Product;
import com.BuyNest.backend.Model.ProductDTO;
import com.BuyNest.backend.Model.Users;
import com.BuyNest.backend.Repository.ProductRepo;
import com.BuyNest.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    @Autowired
    private UserRepo userRepo;


    public void setRepo(ProductRepo repo) {
        this.repo = repo;
    }

    public List<ProductDTO> convertToSellerProductDTOList(List<Product> products) {
        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
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
            dto.setDeleted(product.isDeleted());

            // Set seller's name safely
            if (product.getSeller() != null) {
                dto.setSellerName(product.getSeller().getUsername()); // Or getFullName(), whatever you have
            }

            dtoList.add(dto);
        }

        return dtoList;
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
        dto.setDeleted(product.isDeleted());

        // Set seller's name safely
        if (product.getSeller() != null) {
            dto.setSellerName(product.getSeller().getUsername());
        }
        return dto;
    }

    public List<ProductDTO> getProducts() {
        return convertToSellerProductDTOList(repo.findAll());
    }

    public ProductDTO addProduct(Product product, MultipartFile imageFile, Principal principal) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        product.setDeleted(false);

        Users u = userRepo.findByUsername(principal.getName());

        product.setSeller(u);
        return convertToDTO(repo.save(product));
    }

    public void addList(List<Product> products) {
        for (Product p : products) {
            repo.save(p);
        }
    }

    public ProductDTO getProductById(Long id) {
        Product product= repo.findById(id).orElse(null);
        if(product!=null){
            return convertToDTO(product);
        }

        return null;
    }

    public ProductDTO updateProduct(Product product, MultipartFile imageFile, Principal principal) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        Users user = userRepo.findByUsername(principal.getName());
        product.setSeller(user);
        System.out.println(product);
        return convertToDTO(repo.save(product));
    }

    public void deleteProduct(Long id) {
        Product product=repo.findById(id).orElse(null);
        if(product!=null){
            product.setDeleted(true);
            repo.save(product);
        }


    }

    public List<ProductDTO> getSearchedProduct(String keyword) {
        return convertToSellerProductDTOList(repo.searchProduct(keyword));


    }

    public List<ProductDTO> getSellerProduct(Principal principal) {
        Users user = userRepo.findByUsername(principal.getName());

        List<Product> products = repo.findBySeller(user);

        return convertToSellerProductDTOList(products);
    }

}
