package com.ecommerce.app.appuser;

import com.ecommerce.app.appuser.user.AppUser;
import com.ecommerce.app.appuser.user.AppUserDTO;
import com.ecommerce.app.security.request.SignupRequest;
import com.ecommerce.app.util.PageDetails;

public interface AppUserService
{
    AppUserDTO createUser(SignupRequest signupRequest);
    String getUserName(long userId);
    PageDetails<AppUserDTO> getAllUsers(int pageNum, int pageSize, String sortBy, String sortOrder);
    AppUser getUser(long userId);
}
