package com.leemo.leemo.service;


import com.leemo.leemo.entity.Users;
import com.leemo.leemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createUser(Users users){
       this.userRepository.save(users);
    }
    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }
//    public Users updateUser()

}
