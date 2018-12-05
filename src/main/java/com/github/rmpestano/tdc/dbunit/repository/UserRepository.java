package com.github.rmpestano.tdc.dbunit.repository;

import javax.transaction.Transactional;

import com.github.rmpestano.tdc.dbunit.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * @param email the user email.
     */
    User findByEmail(String email);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.tweets where u.email = :email")
    User findByEmailWithTweets(@Param("email") String email);

}
