package com.puc.barbershop.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.User;
import com.puc.barbershop.repository.UserRepository;
import com.puc.barbershop.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>>getUsers(){

        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User>getUser(@PathVariable(value = "username") String username){
        Optional<User> userOptional= userService.getUser(username);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            User user = userOptional.get();
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/save")
    public ResponseEntity<User>saveUser(@RequestBody User user){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<User>updateUser(@PathVariable(value = "username") String username, @RequestBody User user){
        Optional<User> userOptional= userService.getUser(username);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            user.setId(userOptional.get().getId());
            return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.OK);
        }
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username){
        Optional<User> userOptional= userService.getUser(username);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            User user = userOptional.get();
            userService.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addRoleToUser")
    public ResponseEntity<Role>addRoleToUser(@RequestBody RoleToUserForm roleToUserForm){

        userService.addRoleToUser(roleToUserForm.getUsername(), roleToUserForm.getRoleName());

        return ResponseEntity.ok().build();
    }

//    @GetMapping(path = "registration/confirm")
//    public String confirm(@RequestParam("token") String token) {
//        return userService.confirmToken(token);
//    }


    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Optional<User> userOptional= userService.getUser(username);
                if(!userOptional.isPresent()){
                    throw new RuntimeException("User not found in database!");
                }else {
                    User user = userOptional.get();

                    String access_token = JWT.create()
                            .withSubject(user.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                            .withIssuer(request.getRequestURI().toString())
                            .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                            .sign(algorithm);
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }
            } catch(Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_msg", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        }else{
            throw new RuntimeException("Refresh token is missing!");
        }

    }

}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
