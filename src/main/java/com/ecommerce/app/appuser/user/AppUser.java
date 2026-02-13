package com.ecommerce.app.appuser.user;

import com.ecommerce.app.appuser.address.UserAddress;
import com.ecommerce.app.appuser.role.UserRole;
import com.ecommerce.app.util.AppUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = AppUser.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    })
public class AppUser
{
    public static final String TABLE_NAME = AppUtil.TABLE_NAME_PREFIX + "user";
    public static final String USER_TO_ROLE = TABLE_NAME + "_to_role";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public AppUser(){}

    public AppUser(String name, String email, String pwd)
    {
        this.userName = name;
        this.email = email;
        this.password = pwd;
    }

    @OneToMany(
            mappedBy = "appUser",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<UserRole> roles;

    @OneToMany(
            mappedBy = "appUser",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<UserAddress> addresses;


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }


    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddress> addresses) {
        this.addresses = addresses;
    }
}
