package com.ecommerce.app.controller;

import com.ecommerce.app.appuser.AppUserService;
import com.ecommerce.app.appuser.user.AppUser;
import com.ecommerce.app.appuser.user.AppUserDTO;
import com.ecommerce.app.appuser.user.AppUserRepo;
import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.security.jwt.JwtUtil;
import com.ecommerce.app.security.request.LoginRequest;
import com.ecommerce.app.security.request.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController
{

    private final AppUserRepo userRepo;
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthRestController(AppUserRepo userRepo, AppUserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //already existing user
    @PostMapping("/signin")
    public ResponseEntity<APIResponse<Void>> loginUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Optional<AppUser> user = userRepo.findByEmail(loginRequest.getEmail());

        if(user.isEmpty())
        {
            return new ResponseEntity<>(
                    new APIResponse<>(
                            "Email not exists. Please register and try again!",
                            false,
                            null
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }

        AppUser appUser = user.get();
        if(!loginRequest.getPassword().equals(appUser.getPassword()))
        {
            return new ResponseEntity<>(
                    new APIResponse<>(
                            "Invalid Password!",
                            false,
                            null
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }

            //todo - password should be encrypted while saving in db
//        if(!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword()))
//        {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password!!");
//        }

//        String token = jwtUtil.generateToken(appUser.getEmail());
//
//        return ResponseEntity.ok(token);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Logged in successfully!",
                        true,
                        null
                ),
                HttpStatus.OK
        );
    }

    //new user
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<AppUserDTO>> registerUser(@Valid @RequestBody SignupRequest signupRequest)
    {

        if(userRepo.existsByEmail(signupRequest.getEmail()))
        {
            return new ResponseEntity<>(
                    new APIResponse<>(
                            "Email already exists!",
                            false,
                            null
                    ),
                    HttpStatus.CONFLICT
            );
        }

        AppUserDTO userDTO = userService.createUser(signupRequest);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "User successfully registered!!",
                        true,
                        userDTO
                ),
                HttpStatus.CREATED
        );
    }
}
