package com.ecommerce.app.appuser.user;

import com.ecommerce.app.appuser.address.UserAddressDTO;
import com.ecommerce.app.appuser.role.UserRole;

import java.util.List;

import static com.ecommerce.app.appuser.role.UserRole.*;

public class AppUserDTO
{
    public long userId;
    public String userName;
    public String email;
    public String password;
    public List<String> roles;
    public List<UserAddressDTO> userAddressDTOS;

    public AppUserDTO(){}

    public AppUserDTO(String userName, String email)
    {
        this.userName = userName;
        this.email = email;
    }
}
