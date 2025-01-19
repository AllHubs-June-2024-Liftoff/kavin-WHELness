//package org.launchcode.PlatePlanner.model;
//
//import java.util.Date;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name="users")
//public class AppUser {
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    private int id;
//
//    private String firstName;
//    private String lastName;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String phone;
//    private String address;
//    private String password;
//    private String role;
//    private Date createdAt;
//
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
//    public String getFirstName() {
//        return firstName;
//    }
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//    public String getLastName() {
//        return lastName;
//    }
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getPhone() {
//        return phone;
//    }
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//    public String getAddress() {
//        return address;
//    }
//    public void setAddress(String address) {
//        this.address = address;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//}
//
