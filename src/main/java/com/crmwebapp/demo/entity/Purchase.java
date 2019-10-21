package com.crmwebapp.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import com.crmwebapp.demo.PaymentMethod;

@Entity
@Table(name = "purchase")
public class Purchase extends com.crmwebapp.demo.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "is required")
    @Column(name = "date_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss.SSS")
    private Date dateTime;

    @NotNull(message = "is required")
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "is required")
    @Column(name = "total_spending")
    private float totalSpending;

    @NotNull(message = "please indicate one customer")
    @ManyToOne(fetch = FetchType.EAGER/*
										 * , cascade=
										 * {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.
										 * REFRESH}
										 */)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", updatable = false)
    private Customer customer;

    @NotEmpty(message = "please indicate one or more products")
    @ManyToMany(fetch = FetchType.EAGER/*
										 * , cascade=
										 * {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.
										 * REFRESH}
										 */)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;


    public Purchase() {
        this.dateTime = new Date();
    }

    public Purchase(PaymentMethod paymentMethod, Customer customer, List<Product> products) {
        this.dateTime = new Date();
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.products = products;
        this.calcAndSetTotalSpending();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(float totalSpending) {
        this.totalSpending = totalSpending;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.calcAndSetTotalSpending();
    }

    public void calcAndSetTotalSpending() {
        float total = 0;

        for (Product product : products) {
            total += product.getPrice();
        }
        this.setTotalSpending(total);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
        result = prime * result + ((products == null) ? 0 : products.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Purchase other = (Purchase) obj;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        if (products == null) {
            if (other.products != null)
                return false;
        } else if (!products.equals(other.products))
            return false;
        return true;
    }

    /**
     * Compare two Purchase obj by considering all attributes except ids.
     *
     * @param other the reference object with which to compare.
     * @return true if this object and the argument object have equal attributes, ignoring ids; false otherwise.
     */
    @Override
    public boolean isJustLike(com.crmwebapp.demo.entity.Entity obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Purchase other = (Purchase) obj;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        if (paymentMethod != other.paymentMethod)
            return false;
        if (products == null) {
            if (other.products != null)
                return false;
        } else if (!products.equals(other.products))
            return false;
        if (Float.floatToIntBits(totalSpending) != Float.floatToIntBits(other.totalSpending))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String products = productsToString();
        String customer = this.customer.getPrimaryKey().toString();
        return "Purchase [id=" + id + ", dateTime=" + dateTime + ", paymentMethod=" + paymentMethod + ", totalSpending="
                + totalSpending + ", customer=" + customer + ", products=[" + products + "]]";
    }

    private String productsToString() {
//		Iterable<Product> products = (Iterable<Product>) getProducts();
        return EntityUtils.entitiesToString(products);
    }

    @Override
    public String prymaryKeyToString() {
        return String.valueOf(getId());
    }

    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public String getDelineation() {
        return getDateTime() + " - " + getCustomer().getCompleteName();
    }

}
