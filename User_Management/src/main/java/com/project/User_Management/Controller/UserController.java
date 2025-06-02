package com.project.User_Management.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.User_Management.Entity.User;
import com.project.User_Management.Repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Update with your frontend port if different
public class UserController {
	
	@Autowired
    private UserRepository userRepository;

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // GET user by user id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + id));
    }

    // Create a New User
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

//     Update a User
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());

        return userRepository.save(user);
    }

    // Delete a User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
    	Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
        	userRepository.deleteById(id);
        	System.out.println("User deleted successfully!");
        } else {
        	throw new RuntimeException("User not found with ID " + id);
        }
    }

}
