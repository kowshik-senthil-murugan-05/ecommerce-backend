package com.ecommerce.app.appuser.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long>
{
    Page<UserAddress> findAllByAppUser_Id(long userId, Pageable pageDetails);

    Optional<UserAddress> findByIdAndAppUser_Id(long addressId, long userId);
}
