package com.lhind.minisocialmedia.controller;

import com.lhind.minisocialmedia.dto.UserDTO;
import com.lhind.minisocialmedia.entity.User;
import com.lhind.minisocialmedia.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UserDTO userDTO){
        User userByEmail = userRepository.findFirstByEmail(userDTO.getEmail());
        if (userByEmail != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = UserDTO.builder()
                .id(user.get().getId())
                .name(user.get().getName())
                .email(user.get().getEmail())
                .build();

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id){
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.get().setName(userDTO.getName());
        user.get().setEmail(userDTO.getEmail());
        userRepository.save(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
