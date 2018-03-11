package com.foo.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.foo.login.model.UserBean;
 
public interface UserRepository extends JpaRepository<UserBean, String> {
 
     UserBean findByEmail(String email);
 
}
