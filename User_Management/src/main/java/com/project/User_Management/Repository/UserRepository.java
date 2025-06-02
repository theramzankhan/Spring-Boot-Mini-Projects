package com.project.User_Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.User_Management.Entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{

}
