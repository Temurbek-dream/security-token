package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>
{
    List<Role> findAllByName(RoleName name);
}
