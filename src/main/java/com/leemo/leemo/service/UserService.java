package com.leemo.leemo.service;


import com.leemo.leemo.entity.Balance;
import com.leemo.leemo.entity.Rating;
import com.leemo.leemo.entity.Users;
import com.leemo.leemo.enums.Roles;
import com.leemo.leemo.enums.Status;
import com.leemo.leemo.repo.BalanceRepository;
import com.leemo.leemo.repo.RatingRepository;
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
    @Autowired
    RatingRepository repository;

    public void save(Users users) {
        users.setCreatedDate(new Date());
        users.setStatus(Status.ACTIVE);
        users.setActive(true);
        users.setRating(new Rating());
        if (users.getRole() != Roles.ADMIN || users.getRole() != Roles.USER) {
            Balance balance = new Balance();
            balance.setStatus(Status.ACTIVE);
            users.setBalance(balance);
        }
        this.userRepository.save(users);
        repository.save(users.getRating());
    }

    public Users findUser(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void deactivateUser(Long id, Status status) {
        Users user = findUser(id);
        if (user != null) {
            user.setStatus(status);
        }
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findFirstByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    public Users findByMail(String mail) {
        return userRepository.findByEmail(mail);
    }

    public void Update(Users users) {
        this.userRepository.save(users);
    }

    public Double getUserRate(Long id) {
        Users users = findUser(id);
        Rating rating = users.getRating();
        return rating.getRate() / rating.getRates();
    }

    public Boolean chekUser(String email){
       Users user = this.userRepository.findByEmailAndStatus(email, Status.ACTIVE);
        return user != null;
    }
}