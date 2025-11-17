package com.example.store.controllers;


import com.example.store.dtos.UserDto;
import com.example.store.entities.User;
import com.example.store.mappers.UserMapper;
import com.example.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping()
    public Iterable<UserDto> getAllUsers() {
        /*
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
         */
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    //"/users/{id}")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
        //return ResponseEntity.ok(user);
        //var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        //return ResponseEntity.ok(userDto);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
