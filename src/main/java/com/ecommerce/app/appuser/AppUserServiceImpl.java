package com.ecommerce.app.appuser;

import com.ecommerce.app.appuser.address.UserAddressDTO;
import com.ecommerce.app.appuser.role.UserRole;
import com.ecommerce.app.appuser.user.AppUser;
import com.ecommerce.app.appuser.user.AppUserDTO;
import com.ecommerce.app.appuser.user.AppUserRepo;
import com.ecommerce.app.exceptionhandler.APIException;
import com.ecommerce.app.security.request.SignupRequest;
import com.ecommerce.app.util.PageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService
{
    private final AppUserRepo appUserRepo;

    public AppUserServiceImpl(AppUserRepo appUserRepo)
    {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public AppUserDTO createUser(SignupRequest signupRequest)
    {
        AppUser appUser = new AppUser();

        appUser.setUserName(signupRequest.getUsername());
        appUser.setEmail(signupRequest.getEmail());
        appUser.setPassword(signupRequest.getPassword());

        if(!signupRequest.getRole().isEmpty())
        {
            List<UserRole> userRoles = signupRequest.getRole().stream()
                    .map(str -> {
                        UserRole.Role roleEnum = UserRole.Role.valueOf(str);

                        UserRole userRole = new UserRole(roleEnum);
                        userRole.setAppUser(appUser);

                        return userRole;
                    }).toList();

            appUser.setRoles(userRoles);
        }

        AppUser user = appUserRepo.save(appUser);

        return new AppUserDTO(user.getUserName(), user.getEmail());
    }

    @Override
    public PageDetails<AppUserDTO> getAllUsers(int pageNum, int pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortByAndOrder);
        Page<AppUser> users = appUserRepo.findAll(pageDetails);

        if(users.isEmpty())
        {
            throw new APIException("No Users Available!");
        }

        List<AppUserDTO> userDTOS = users.stream().map(this::convertToDTO).toList();

        return new PageDetails<>(
                userDTOS,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isLast()
        );
    }

    @Override
    public AppUser getUser(long userId) {
        return appUserRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id!!"));
    }

    private AppUserDTO convertToDTO(AppUser appUser)
    {
        AppUserDTO dto = new AppUserDTO();

        dto.userId = appUser.getId();
        dto.userName = appUser.getUserName();
        dto.email = appUser.getEmail();

        dto.userAddressDTOS = appUser.getAddresses().stream().map(addr ->
        {
            UserAddressDTO addressDTO = new UserAddressDTO();
            addressDTO.userAddressId = addr.getAddressId();
            addressDTO.buildingNum = addr.getBuildingNo();
            addressDTO.buildingName = addr.getBuildingName();
            addressDTO.streetNum = addr.getStreetNo();
            addressDTO.streetName = addr.getStreetName();
            addressDTO.buildingName = addr.getBuildingName();
            addressDTO.city = addr.getCity();
            addressDTO.pincode = addr.getPincode();

            return addressDTO;
        }).collect(Collectors.toList());

        if(!appUser.getRoles().isEmpty())
        {
            dto.roles = appUser.getRoles().stream()
                    .map(u -> u.getRoleName().label())
                    .toList();
        }

        return dto;
    }

    @Override
    public String getUserName(long userId)
    {
        System.out.println("App user id -> " + userId);
        return appUserRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id!!"))
                .getUserName();
    }


}
