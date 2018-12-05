/*
 * Copyright 2018 rmpestano.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rmpestano.tdc.dbunit;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.github.rmpestano.tdc.dbunit.model.User;
import com.github.rmpestano.tdc.dbunit.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author rmpestano
 */
@RunWith(SpringRunner.class)
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) @ActiveProfiles("h2-test")
@SpringBootTest
//@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
public class UserRepositoryDBUnitIt {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DataSet(value = "users.yml")
    @ExportDataSet(includeTables = "users", outputName = "users.yml")
    public void shouldListUsers() {
        assertThat(userRepository.count()).isEqualTo(3);
        assertThat(userRepository.findByEmail("springboot@gmail.com"))
                .isEqualTo(new User(3));
    }

    @Test
    @DataSet(value = "users_tweets.yml")
    public void shouldListUsersAndTweets() {
        assertThat(userRepository.count()).isEqualTo(2);
        assertThat(userRepository.findByEmailWithTweets("dbunit@gmail.com"))
                .isEqualTo(new User(1));
    }


    @Test
    @DataSet(value="users.yml")
    public void shouldDeleteUser() {
        assertThat(userRepository).isNotNull();
        assertThat(userRepository.count()).isEqualTo(3);
        userRepository.delete(userRepository.findOne(2L));
        assertThat(userRepository.count()).isEqualTo(2);
        assertThat(userRepository.findOne(2L)).isNull();
    }


    @Test
    @DataSet(cleanBefore = true)//as we didn't declared a dataset DBUnit wont clear the table
    public void shouldInsertUser() {
        assertThat(userRepository).isNotNull();
        assertThat(userRepository.count()).isEqualTo(0);
        userRepository.save(new User("newUser@gmail.com", "new user"));
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(userRepository.findByEmail("newUser@gmail.com")).isNotNull();
    }

}
