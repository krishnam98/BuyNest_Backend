package com.amazonCklone.backend.Controller;

import com.amazonCklone.backend.Model.Product;
import com.amazonCklone.backend.Model.ProductDTO;
import com.amazonCklone.backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setService(ProductService service){
        this.service=service;
    }

    @GetMapping("/home")
    public String greet(){
        return "HelloWorld";
    }

//    Get all products

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK) ;
    }

//    Get product by id

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        System.out.println(id);
        ProductDTO productDto=service.getProductById(id);
        System.out.println(productDto);
        if(productDto!=null){

            return new ResponseEntity<>(productDto,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    Get product's image by id

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long productId){
        ProductDTO product= service.getProductById(productId);
        byte[] imageFile= product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

//    Get searched product

    @GetMapping("/searchProduct/{keyword}")
    public List<ProductDTO> searchProduct(@PathVariable String keyword){
        return service.getSearchedProduct(keyword);
    }

//  get seller products

    @GetMapping("/getSellerProducts")
    public List<ProductDTO> getSellerProduct(Principal principal){
        return service.getSellerProduct(principal);
    }

//  Post mappings

    @PostMapping("/addList")
    public void addList(@RequestBody List<Product> products){
        service.addList(products);
    }


//    Add product
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile,
                                        Principal principal){

        try{
//            Product product = objectMapper.readValue(productString, Product.class);
           ProductDTO product1 = service.addProduct(product,imageFile,principal);
           return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

//  update product

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestPart Product product,
                                                 @RequestPart MultipartFile imageFile ){

        ProductDTO product1=null;
        try {

             product1=service.updateProduct(product,imageFile);

        }
        catch(Exception e){
            System.out.println(e);
               return  new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
        if(product1!=null){
            return new ResponseEntity<>("updated",HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("Error",HttpStatus.BAD_REQUEST);
        }



    }

//    Delete product by id

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){

        try{
            service.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted!",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error deleting Product",HttpStatus.BAD_REQUEST);
        }
    }


}
