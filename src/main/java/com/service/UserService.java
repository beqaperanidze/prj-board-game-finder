package com.service;

import com.entity.User;
import com.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return userRepo.findById(id).get();
        }
        return null;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> addMultipleUsers(List<User> users) {
        return userRepo.saveAll(users);
    }
}
