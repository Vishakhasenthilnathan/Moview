package moviereview.moviereview.controller;

import moviereview.moviereview.models.ERole;
import moviereview.moviereview.models.User;
import moviereview.moviereview.models.Role;
import moviereview.moviereview.payload.LoginDTO;
import moviereview.moviereview.payload.SignUpDTO;
import moviereview.moviereview.repository.RoleRepository;
import moviereview.moviereview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController() {
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDTO signUpDTO){
        if(userRepository.existsByUsername(signUpDTO.getUsername()))
            return new ResponseEntity<>("Username exists already",HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(signUpDTO.getEmail()))
            return new ResponseEntity<>("Email already exists",HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role roles = roleRepository.findByRole(ERole.ROLE_Admin).get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
        return new ResponseEntity<>("User signed up successfully",HttpStatus.OK);
    }
    @GetMapping("/users")
    public String getUsers(){
        return userRepository.getById(Long.valueOf(1)).getUsername();
    }
}
