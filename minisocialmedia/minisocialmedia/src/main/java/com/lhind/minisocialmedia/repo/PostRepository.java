package com.lhind.minisocialmedia.repo;

import com.lhind.minisocialmedia.entity.Post;
import com.lhind.minisocialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByUser(User user);
    public List<Post> findByUserAndTitle(User user, String title);
}
