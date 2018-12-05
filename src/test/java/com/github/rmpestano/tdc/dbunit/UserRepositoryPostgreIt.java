package com.github.rmpestano.tdc.dbunit;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.github.rmpestano.tdc.dbunit.model.User;
import com.github.rmpestano.tdc.dbunit.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) @ActiveProfiles("postgres-test")
@DBRider //enables database rider in spring tests
public class UserRepositoryPostgreIt {

    private static final PostgreSQLContainer postgres = new PostgreSQLContainer(); //creates the database for all tests on this file 


    @Autowired
    private UserRepository userRepository;


    @BeforeClass
    public static void setupContainer() {
        postgres.start();
    }

    @AfterClass
    public static void shutdown() {
        postgres.stop();
    }

    @Test
    @DataSet("users.yml")
    public void shouldListUsers() {
        assertThat(userRepository.count()).isEqualTo(3);
        assertThat(userRepository.findByEmail("springboot@gmail.com")).isEqualTo(new User(3));
    }

    @Test
    @DataSet("users.yml")
    @ExpectedDataSet("expectedUsers.yml")
    public void shouldDeleteUser() {
        assertThat(userRepository.count()).isEqualTo(3);
        userRepository.delete(userRepository.findOne(2L));
        //assertThat(userRepository.count()).isEqualTo(2); //assertion is made by @ExpectedDataset
    }


    @Test
    @DataSet(value="empty.yml")
    @ExpectedDataSet("user.yml")
    public void shouldInsertUser()  {
        assertThat(userRepository.count()).isEqualTo(0);
        userRepository.save(new User("newUser@gmail.com", "new user"));
        //assertThat(userRepository.count()).isEqualTo(1); //assertion is made by @ExpectedDataset
    }
}
