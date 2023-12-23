package com.dkkhoa.possystem.model.products;

import com.dkkhoa.possystem.model.saledetail.SaleDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "import_price")
    private int importPrice;
    @Column(name = "retail_price")
    private int retailPrice;
    @Column(name="manufacturer")
    private String manufacturer;
    @Column(name="category")
    private String category;
    @Column(name="image")
    private String image;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SaleDetail> salesTransactions;


    @Service
    public static class ProductService {

        @Autowired
        private ProductRepository productRepository;

        public List<Product> getAllProducts() {
            return productRepository.findAllByOrderByCreationDateDesc();
        }

        public Product getProductById(Long id) {
            return productRepository.findById(id).orElse(null);
        }

        public void saveProduct(Product product) {
            productRepository.save(product);
        }

        public void deleteProduct(Long id) {
            if (productRepository.existsByIdAndSalesTransactionsIsEmpty(id)) {
                productRepository.deleteById(id);
            } else {
                throw new RuntimeException("Product has associated sales transactions and cannot be deleted.");
            }
        }

    }


}
