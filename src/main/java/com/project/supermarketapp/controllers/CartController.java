package com.project.supermarketapp.controllers;

import com.project.supermarketapp.entities.Customer;
import com.project.supermarketapp.entities.Product;
import com.project.supermarketapp.entities.ShoppingCart;
import com.project.supermarketapp.entities.User;
import com.project.supermarketapp.services.ProductService;
import com.project.supermarketapp.services.ShoppingCartService;
import com.project.supermarketapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    public CartController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }


//<------------------------------------Get Request Handler----------------------------------------------->


           @GetMapping("/shoppingCart")
            public String cart(Model model, Principal principal, HttpSession session) {

               //        <--------------------User hasn't logged in---------------------------->
               if (principal == null) {
                   return "redirect:/login";
                           }
                   //        <--------------------User hasn't logged in---------------------------->

                   //        <---------------------Find customer from his username-------------------->
                   String username = principal.getName();
                   User user = userService.findByUsername(username);
                   ShoppingCart shoppingCart = user.getShoppingCart();
                   //        <---------------------Find customer from his username-------------------->

                   //      <------------------------Setting of data done here------------------------->
                   if (shoppingCart == null) {
                       model.addAttribute("check", "No item in your cart");
                   }
                   session.setAttribute("totalItems", shoppingCart.getTotalItems());
                   model.addAttribute("subTotal", shoppingCart.getTotalPrices());
                   model.addAttribute("shoppingCart", shoppingCart);
                   return "cart";
               }
               //      <------------------------Setting of data ends------------------------------->



//<------------------------------------Get Request Handler----------------------------------------------->


//<------------------------------------Post Request Handler---------------------------------------------->


            @PostMapping("/add-to-cart")
            public String addItemToCart(@RequestParam("id") Long productId,@RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity, Principal principal,
                    HttpServletRequest request){

                if(principal == null){
                    return "redirect:/login";
                }
                Product product = productService.getProductById(productId);
                String username = principal.getName();
                User user = userService.findByUsername(username);

                ShoppingCart cart = cartService.addItemToCart(product, quantity, user);
                return "redirect:" + request.getHeader("Referer");

            }

//<------------------------------------Post Request Handler---------------------------------------------->


//<-----------------------------------Update Request Handler---------------------------------------------------------------------------------------->


            @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
            public String updateCart(@RequestParam("quantity") int quantity, @RequestParam("id") Long productId, Model model, Principal principal){
                if(principal == null){
                    return "redirect:/login";
                }else{
                    String username = principal.getName();
                    User user = userService.findByUsername(username);
                    Product product = productService.getProductById(productId);
                    ShoppingCart cart = cartService.updateItemInCart(product, quantity, user);

                    model.addAttribute("shoppingCart", cart);
                    return "redirect:/cart";
                }

            }


//<-----------------------------------Update Request Handler---------------------------------------------------------------------------------------->


//<-----------------------------------------Delete Request Handler------------------------------------------------------->


    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemFromCart(@RequestParam("id") Long productId, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }else{
            String username = principal.getName();
            User user= userService.findByUsername(username);
            Product product = productService.getProductById(productId);
            ShoppingCart cart = cartService.deleteItemFromCart(product, user);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }

    }


//<--------------------------------------------Delete Request handler end---------------------------------------------------->
}
