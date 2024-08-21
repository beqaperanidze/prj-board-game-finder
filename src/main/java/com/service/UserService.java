package com.service;

import com.exception.UserNotFoundException;
import com.entity.User;
import com.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void updateUser(Long id, User user) throws UserNotFoundException {
        Optional<User> currUser = userRepo.findById(id);
        if (currUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        } else {
            User temp = currUser.get();
            temp.setId(id);
            temp.setFirstName(user.getFirstName());
            temp.setLastName(user.getLastName());
            temp.setEmail(user.getEmail());
            temp.setPassword(user.getPassword());
            temp.setFavourites(user.getFavourites());
            userRepo.save(temp);
        }
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        Optional<User> currUser = userRepo.findById(id);
        if (currUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        } else {
            userRepo.deleteById(id);
        }
    }
}
