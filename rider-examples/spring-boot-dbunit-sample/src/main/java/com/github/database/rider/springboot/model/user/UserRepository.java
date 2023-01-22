package com.github.database.rider.springboot.model.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   *
   * @param email the user email.
   */
   User findByEmail(String email);

}
