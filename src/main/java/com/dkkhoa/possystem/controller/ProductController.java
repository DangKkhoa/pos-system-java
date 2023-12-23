package com.dkkhoa.possystem.controller;

import com.dkkhoa.possystem.model.products.Product;
import com.dkkhoa.possystem.model.users.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private Product.ProductService productService;

    @GetMapping
    public String getAllProducts(Model model, HttpSession session) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        boolean isAdmin = false;

        User user = (User) session.getAttribute("user");
        if (user != null) {
            isAdmin = user.isAdmin();
        }

        System.out.println("User: " + user);
        System.out.println("isAdmin: " + isAdmin);

        model.addAttribute("isAdmin", isAdmin);
        return "products/list";
    }


    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/add";
    }
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product,Model model) {
        product.setCreationDate(LocalDate.now());

        productService.saveProduct(product);
        model.addAttribute("successMessage", "Product added successfully!");


        return "products/add";
    }








    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable Long id, Model model,HttpSession session) {
        boolean isAdmin = false;

        User user = (User) session.getAttribute("user");
        if (user != null) {
            isAdmin = user.isAdmin();
        }

        System.out.println("User: " + user);
        System.out.println("isAdmin: " + isAdmin);

        model.addAttribute("isAdmin", isAdmin);
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/products/details";
    }





    @GetMapping("/{productId}/edit")
    public String showEditForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);

        model.addAttribute("product", product);

        return "/products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, Product updatedProduct) {
        updatedProduct.setId(id);

        productService.saveProduct(updatedProduct);

        return "redirect:/products/" + id;
    }


    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
