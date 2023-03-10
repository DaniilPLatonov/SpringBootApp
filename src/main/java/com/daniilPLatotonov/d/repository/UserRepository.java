package com.daniilPLatotonov.d.repository;

import com.daniilPLatotonov.d.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
