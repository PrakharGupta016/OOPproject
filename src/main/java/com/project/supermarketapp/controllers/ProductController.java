package com.project.supermarketapp.controllers;

import com.project.supermarketapp.entities.User;
import com.project.supermarketapp.payloads.ApiResponse;
import com.project.supermarketapp.payloads.ProductDto;
import com.project.supermarketapp.entities.Category;
import com.project.supermarketapp.entities.Product;
import com.project.supermarketapp.respository.CategoryRepo;
import com.project.supermarketapp.services.ProductService;
import com.project.supermarketapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
@CrossOrigin
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("category does not exists", false), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<ApiResponse>(new ApiResponse("product has been added", true), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // create an api to edit the product

    @GetMapping(value = "/product/{name}")
        public Product getProductbyName(@PathVariable String name)
    {
        return productService.getProductByName(name);
    }


    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("category does not exists", false), HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto, productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("product has been updated", true), HttpStatus.OK);
    }
    // Find product by category
     @GetMapping("/category/{CatId}")
    public ResponseEntity<List<Product>>getProductByCategory(@PathVariable int CatId)
     {
        List<Product>findProductByCategory=this.productService.findProductByCategory(CatId);
        return new ResponseEntity<List<Product>>(findProductByCategory, HttpStatus.ACCEPTED);

     }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Integer id){
        this.productService.deleteProductById(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Product deleted Successful",true),HttpStatus.OK);
}}