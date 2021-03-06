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

import com.github.rmpestano.tdc.dbunit.model.User;
import com.github.rmpestano.tdc.dbunit.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rmpestano
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("h2-test")
@SpringBootTest
//@Ignore
public class UserRepositoryIt {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void shouldFindByEmail() {
        userRepository.save(new User("springboot@gmail.com", "SpringBoot"));
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(userRepository.findByEmail("springboot@gmail.com")).isNotNull();
    }

    @Test
    public void shouldInsertUser() {
        long countBefore = userRepository.count();
        userRepository.save(new User("newUser@gmail.com", "new user"));
        assertThat(userRepository.count()).isEqualTo(countBefore + 1);
    }

    @Test
    public void shouldDeleteUser() {
        userRepository.save(new User("newUser@gmail.com", "new user"));
        assertThat(userRepository.count()).isEqualTo(1);
        userRepository.delete(userRepository.findByEmail("newUser@gmail.com"));
        assertThat(userRepository.count()).isEqualTo(0);
    }

}