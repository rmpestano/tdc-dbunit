package com.github.rmpestano.tdc.dbunit.repository;

import com.github.rmpestano.tdc.dbunit.model.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface TweetRepository extends CrudRepository<Tweet, String> {



}
