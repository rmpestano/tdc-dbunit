package com.github.rmpestano.tdc.dbunit.repository;

import com.github.rmpestano.tdc.dbunit.model.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TweetRepository extends CrudRepository<Tweet, String> {


    @Query("Select t from Tweet t where t.content LIKE  %:content%")
    List<Tweet> findByContent(@Param("content") String content);
}
