package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "AddressLine1" , nullable = false , length = 225)
    private String AddressLine1;

    @Column (name = "AddressLine2" , nullable = false , length = 225)
    private String AddressLine2;

    @Column (name = "City" , nullable = false)
    private String City;

    @Column (name = "Street" , nullable = false)
    private String Street;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address() {}

    public String getAddressLine1(){
        return AddressLine1 ;
    }
    public void setAddressLine1(String AddressLine1){
        this.AddressLine1 = AddressLine1 ;
    }
    public String getAddressLine2(){
        return AddressLine2 ;
    }
    public void setAddressLine2(String AddressLine2){
        this.AddressLine2 = AddressLine2 ;
    }
    public String getCity(){
        return City ;
    }
    public void setCity(String City){
        this.City = City ;
    }
    public String getStreet(){
        return Street ;
    }
    public void setStreet(String Street){
        this.Street = Street ;
    }


}
