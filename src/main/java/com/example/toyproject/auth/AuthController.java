package com.example.toyproject.auth;

import com.example.toyproject.auth.dto.SignInRequest;
import com.example.toyproject.auth.dto.SignUpRequest;
import com.example.toyproject.auth.model.ERole;
import com.example.toyproject.auth.model.Role;
import com.example.toyproject.auth.model.User;
import com.example.toyproject.auth.repository.RoleResitory;
import com.example.toyproject.auth.repository.UserRepository;
import com.example.toyproject.config.jwt.dto.JwtResponse;
import com.example.toyproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleResitory roleResitory;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        JwtResponse res = new JwtResponse().builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is already taken");
        }
        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }

        String hassedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleResitory.findByName(ERole.ROLE_USER);
        if(userRole.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
        }
        roles.add(userRole.get());

        User user = new User().builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(hassedPassword)
                .roles(roles)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered success");
    }
}
