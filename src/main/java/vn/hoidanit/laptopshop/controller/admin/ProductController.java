package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {

        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String getproductCreate(@ModelAttribute("newProduct") @Valid Product hoidanit,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");

        hoidanit.setImage(image);

        // save
        productService.handleSaveProduct(hoidanit);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);

        model.addAttribute("product", product);
        return "/admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("updateProduct", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String getUserUpdate(Model model, @ModelAttribute("updateProduct") @Valid Product hoidanit,
            BindingResult updateProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = updateProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if (updateProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product currentProduct = this.productService.getProductById(hoidanit.getId());

        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String image = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(image);
            }
            currentProduct.setName(hoidanit.getName());
            currentProduct.setPrice(hoidanit.getPrice());
            currentProduct.setDetailDesc(hoidanit.getDetailDesc());
            currentProduct.setShortDesc(hoidanit.getShortDesc());
            currentProduct.setFactory(hoidanit.getFactory());
            currentProduct.setQuantity(hoidanit.getQuantity());
            currentProduct.setTarget(hoidanit.getTarget());
            this.productService.handleSaveProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newProduct") Product product) {
        System.out.println("run here");
        this.productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }
}
