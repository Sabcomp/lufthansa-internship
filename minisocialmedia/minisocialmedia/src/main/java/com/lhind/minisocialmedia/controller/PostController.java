package com.lhind.minisocialmedia.controller;

import com.lhind.minisocialmedia.dto.PostDTO;
import com.lhind.minisocialmedia.entity.Post;
import com.lhind.minisocialmedia.entity.User;
import com.lhind.minisocialmedia.repo.PostRepository;
import com.lhind.minisocialmedia.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping("/{id}/posts")
    public ResponseEntity<Void> createPost(@PathVariable Long id, @RequestBody PostDTO postDTO){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user.get());

        postRepository.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable Long id, @RequestParam(required = false) String title){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<PostDTO> posts;
        if (title == null){
             posts = postRepository.findByUser(user.get())
                    .stream()
                    .map(post -> PostDTO.builder().id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .build())
                    .collect(Collectors.toList());
        } else {
            posts = postRepository.findByUserAndTitle(user.get(), title)
                    .stream()
                    .map(post -> PostDTO.builder().id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .build())
                    .collect(Collectors.toList());
        }

        if(posts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
