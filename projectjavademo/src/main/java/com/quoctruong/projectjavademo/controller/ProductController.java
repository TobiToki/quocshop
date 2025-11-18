package com.quoctruong.projectjavademo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.quoctruong.projectjavademo.model.Product;
import com.quoctruong.projectjavademo.service.ProductService;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/search")
    public String searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {
        
        List<Product> products = productService.searchProducts(keyword, category, minPrice, maxPrice);
        List<String> categories = productService.getAllCategories();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        
        return "product/search";
    }

    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        
        if (product == null) {
            return "redirect:/home";
        }
        
        // Get related products (same category)
        List<Product> relatedProducts = productService.getProductsByCategory(product.getCategory(), 4);
        
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        
        return "product/detail";
    }

    @GetMapping("/products/gender/{gender}")
    public String productsByGender(@PathVariable String gender, Model model) {
        // Map incoming path to a list of category keywords to match
        List<String> keywords = new ArrayList<>();

        if ("nam".equalsIgnoreCase(gender)) {
            // match categories that indicate men's items
            keywords.add("nam");
            keywords.add("quần");
            keywords.add("áo nam");
        } else if ("nu".equalsIgnoreCase(gender) || "nữ".equalsIgnoreCase(gender)) {
            // match categories that indicate women's items (include 'váy')
            keywords.add("nữ");
            keywords.add("váy");
            keywords.add("áo nữ");
        } else {
            // unknown gender, redirect to home
            return "redirect:/";
        }

        List<Product> products = productService.getProductsByCategoryKeywords(keywords);
        List<String> categories = productService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", null);
        model.addAttribute("selectedCategory", null);
        model.addAttribute("minPrice", null);
        model.addAttribute("maxPrice", null);

        return "product/search";
    }
}