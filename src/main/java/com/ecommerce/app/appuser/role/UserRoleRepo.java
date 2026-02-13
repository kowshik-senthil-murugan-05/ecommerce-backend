package com.ecommerce.app.appuser.role;

import com.ecommerce.app.appuser.role.UserRole.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRoleName(Role roleName);

}
