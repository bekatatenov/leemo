package com.leemo.leemo.service;


import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Users;
import com.leemo.leemo.enums.Roles;
import com.leemo.leemo.enums.Status;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BalanceRepository balanceRepository;

    public void save(Users users){
        users.setCreatedDate(new Date());
        users.setStatus(Status.ACTIVE);
        users.setActive(true);
        if (users.getRole()!= Roles.ADMIN|| users.getRole()!= Roles.USER) {
            Balance balance = new Balance();
            balance.setStatus(Status.ACTIVE);
            users.setBalance(balance);
        }
        this.userRepository.save(users);
    }
    public Users findUser(Long id){
       return this.userRepository.findById(id).orElse(null);
    }
    public void deactivateUser(Long id, Status status){
        if (findUser(id)!=null){
            findUser(id).setStatus(status);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findFirstByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
