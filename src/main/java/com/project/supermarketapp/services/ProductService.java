package com.project.supermarketapp.services;

import com.project.supermarketapp.entities.Category;
import com.project.supermarketapp.entities.Product;
import com.project.supermarketapp.entities.User;
import com.project.supermarketapp.entities.Wallet;
import com.project.supermarketapp.exceptions.ProductNotExistsException;
import com.project.supermarketapp.exceptions.ResourceNotFoundException;
import com.project.supermarketapp.exceptions.WalletException;
import com.project.supermarketapp.payloads.ProductDto;
import com.project.supermarketapp.payloads.UserDto;
import com.project.supermarketapp.respository.CategoryRepo;
import com.project.supermarketapp.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepo categoryRepo;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getId());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setId(product.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: allProducts) {
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        // throw an exception if product does not exists
        if (!optionalProduct.isPresent()) {
            throw new Exception("product not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        productRepository.save(product);
    }

    public Product getProductByName(String name)
    {
       return productRepository.findByName(name);


    }
    public Product getById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new ProductNotExistsException("Product does not exist");

    }
    //find product by category
    public List<Product>findProductByCategory(int CatId)
    {
       Category Cat =this.categoryRepo.findById(CatId).orElseThrow(()->new ResourceNotFoundException("ID Category not found"));
       List<Product>findByCategory =this.productRepository.findByCategory(Cat);
       return findByCategory;
    }
    public void deleteProductById(Integer id) {
        // TODO Auto-generated method stub
        Product product  = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product with this id does not exist"));
        this.productRepository.delete(product);
        return;

    }

}

