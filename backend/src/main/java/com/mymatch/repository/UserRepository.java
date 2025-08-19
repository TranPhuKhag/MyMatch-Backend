package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
