package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    List<Role> findAllByName(RoleName name);
}
