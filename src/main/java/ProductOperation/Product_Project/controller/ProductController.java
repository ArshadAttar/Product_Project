package ProductOperation.Product_Project.controller;

import ProductOperation.Product_Project.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
   List<Product> productList=new ArrayList<>();
   List<Product> cartList=new ArrayList<>();

   {
      productList.add(new Product(1,"Apple",44299.00,45900.0,10));
      productList.add(new Product(2,"SmartWatch",22999.00,25900.0,10));
      productList.add(new Product(3,"Redmi",13999.00,15900.0,10));
      productList.add(new Product(4,"Laptop",41999.00,42900.0,10));
   }
   @GetMapping("/productHomePage")
   public String displayProduct(Model model){
   model.addAttribute("productData",productList);
      return "landingPage";
   }
   @GetMapping("/addProduct")
   public String createObject(Model model){
      model.addAttribute("productObject",new Product());
      return "addProduct";
   }
   @GetMapping("/landingPage")
   public String homePage(Model model){
      model.addAttribute("productObject",new Product());
      return "redirect:/productHomePage";
   }
   @PostMapping("/addProducts")
   public String saveProducts(Product product){
      productList.add(product);
      return "redirect:/productHomePage";
   }
   @GetMapping("/addToCart/{id}")
   public String addToCart(@PathVariable int id) {
      Product product = getProductById(id);
      if (product.getProductQty() > 0) {
         Product addedProduct = getProductInCartById(id);
         if (addedProduct != null) {
            addedProduct.setProductQty(addedProduct.getProductQty() + 1);
         } else {
            cartList.add(new Product(product.getProductId(), product.getProductName(),
                    product.getPrice(), product.getSellingPrice(), 1));
         }
         product.setProductQty(product.getProductQty() - 1);
      }
      return "redirect:/productHomePage";
   }

   @GetMapping("/viewCart")
   public String viewCart(Model model) {
      model.addAttribute("cartItems", cartList);
      return "viewCart";
   }
   private Product getProductById(int id) {
      for (Product product : productList) {
         if (product.getProductId() == id) {
            return product;
         }
      }
      return null;
   }
   private Product getProductInCartById(int id) {
      for (Product product : cartList) {
         if (product.getProductId() == id) {
            return product;
         }
      }
      return null;
   }
}