package com.lhind.minisocialmedia.repo;

import com.lhind.minisocialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findFirstByEmail(String email);
}
