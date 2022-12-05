package com.project.supermarketapp.respository;

import com.project.supermarketapp.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Long> {
}
