package com.crmwebapp.demo.entity;

import java.util.ArrayList;
import java.util.List;

/*import javax.persistence.CascadeType;
 */import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "product")
public class Product extends com.crmwebapp.demo.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @NotNull(message = "is required")
    @PositiveOrZero(message = "the value must be higher or equal to zero")
    @Column(name = "price")
    private Float price;

    @Column(name = "description")
    private String description;

    @NotNull(message = "is required")
    @PositiveOrZero(message = "the value must be higher or equal to zero")
    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @ManyToMany(fetch = FetchType.LAZY/*
										 * , cascade=
										 * {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.
										 * REFRESH}
										 */)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "purchase_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_id"))
    private List<Purchase> purchases;

    @Column(name = "is_active")
    private boolean isActive = true;


    public Product() {

    }

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
        this.isActive = true;
    }

    public Product(String name, String category, float price, String description, int quantityInStock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase thePurchase) {
        if (purchases == null) {
            purchases = new ArrayList<>();
        }
        purchases.add(thePurchase);
    }


    /**
     * isActive say when the element should be considered "available". <br>
     * Example: a product that is in stock could be considered available, and a product that
     * is out of production could be considered unavailable
     *
     * @return isActive status
     */
    public boolean isActive() {
        return isActive;
    }

    /* Necessario a Thymeleaf, che ha bisogno di metodi get */

    /**
     * isActive say when the element should considered "available". <br>
     * Example: a product that is in stock could be considered available, and a product that
     * is out of production could be considered unavailable
     *
     * @return isActive() result
     */
    public boolean getIsActive() {
        return isActive();
    }

    /**
     * isActive say when the element should considered "available". <br>
     * Example: a product that is in stock could be considered available, and a product that
     * is out of production could be considered unavailable
     *
     * @param isActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return 31 integer number
     * @see <a href="https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/">why returning 31</a>
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime;
    }

    /**
     * Two Product are considered equals if they have the same id.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object and the obj argument have the same id; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (!id.equals(other.id))
            return false;
        return true;
    }

    /**
     * Compare two Product obj by considering all attributes except ids and isActive status.
     *
     * @param other the reference object with which to compare.
     * @return true if this object and the argument object have equal attributes, ignoring ids and isActive status; false otherwise.
     */
    @Override
    public boolean isJustLike(com.crmwebapp.demo.entity.Entity obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (quantityInStock == null) {
            if (other.quantityInStock != null)
                return false;
        } else if (!quantityInStock.equals(other.quantityInStock))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String purchases = purchasesToString();
        return "Product [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", description=["
                + description + "], quantityInStock=" + quantityInStock + ", purchases=[" + purchases + "]]";
    }

    private String purchasesToString() {
        return EntityUtils.entitiesToString(purchases);
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
        return getName() + " - " + getPrice() + " - " + getQuantityInStock();
    }

}
