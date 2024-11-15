package com.happiest.APIGatewayJWT.repository;


import com.happiest.APIGatewayJWT.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {

   // Users findByUsername(String username);
    Users findByEmail(String email);


}
