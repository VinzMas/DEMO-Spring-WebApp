package com.crmwebapp.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name = "customer")
public class Customer extends com.crmwebapp.demo.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "is required")
    @Email
    @Column(name = "email")
    private String email;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY/*
									 * , cascade=CascadeType.ALL
									 */)
    @Cascade(CascadeType.ALL)
    private List<Purchase> purchases;

    @Column(name = "is_active")
    private boolean isActive = true;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String email, String address, List<Purchase> purchases) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.purchases = purchases;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
     * Get firstName and lastName
     *
     * @return string containing firstName and lastName separated by one whitespace
     */
    public String getCompleteName() {
        return firstName + ' ' + lastName;
    }


    /**
     * Set firstName and lastName in one shot.
     *
     * @param string containing firstName and lastName separated by whitespaces
     */
    public void setCompleteName(String completeName) {
        final int numParts = 2;

        String[] nameParts = completeName.trim().split("\\s+");
        if (nameParts.length == numParts) {
            this.firstName = nameParts[0];
            this.lastName = nameParts[1];
        } else {
            throw new IllegalArgumentException("Input string must contain exactly " + numParts + " names");
        }
    }


    /**
     * @return 31 type int
     * @see <a href="https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/">why returning 31</a>
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime;
    }

    /**
     * Two Customer are considered equals if they have the same id.
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
        Customer other = (Customer) obj;
        if (!id.equals(other.id))
            return false;
        return true;
    }

    /**
     * Compare two Customer obj by considering all attributes except ids and isActive status.
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
        Customer other = (Customer) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (purchases == null) {
            if (other.purchases != null)
                return false;
        } else if (!purchases.equals(other.purchases))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String purchases = purchasesToString();
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", address=" + address + ", purchases=[" + purchases + "]]";
    }


    private String purchasesToString() {
        return EntityUtils.entitiesToString(getPurchases());
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
        return getCompleteName() + " - " + getEmail();
    }
}
