package org.launchcode.PlatePlanner.dto;

public class UserDto {

    private String username;
    private String password;
    private String email;
    private String role;
    //    private String fullname;


    public UserDto(String email, String password, String role, String username) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
//        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
//    public String getFullname() {
//        return fullname;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//}