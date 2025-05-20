package com.example.Nutri_Nest.repository;

import com.example.Nutri_Nest.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<Users,Integer> {
    Users getByUsername(String username);
}
