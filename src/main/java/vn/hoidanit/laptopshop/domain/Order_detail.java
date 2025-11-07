package vn.hoidanit.laptopshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Order_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // orderId
    // many order_details -> belong to -> one order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // many order_detail -> belong to -> one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private long quantity;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Oder_detail [id=" + id + ", quantity=" + quantity + ", price=" + price + "]";
    }

}
