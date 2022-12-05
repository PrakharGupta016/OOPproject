package com.project.supermarketapp.services;

import com.project.supermarketapp.entities.Customer;
import com.project.supermarketapp.entities.Product;
import com.project.supermarketapp.entities.ShoppingCart;
import com.project.supermarketapp.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {

        ShoppingCart addItemToCart(Product product, int quantity, User user);

        ShoppingCart updateItemInCart(Product product, int quantity,User user);

        ShoppingCart deleteItemFromCart(Product product, User user);
}
