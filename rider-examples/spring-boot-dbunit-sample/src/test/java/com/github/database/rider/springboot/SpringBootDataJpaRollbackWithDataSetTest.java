package com.github.database.rider.springboot;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.github.database.rider.springboot.model.user.User;
import com.github.database.rider.springboot.model.user.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class SpringBootDataJpaRollbackWithDataSetTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DataSet("users.yml")
    @ExpectedDataSet(value = "expectedUsersWhenInsideSpringTX.yml", orderBy = "id", ignoreCols = "id")
    void dbRiderTest_shouldHaveBeenCleaned_dueToSpringRolledBackTX() {
        userRepository.saveAndFlush(new User("bdd@cucumber.com", "cucumber"));
    }

    @Test
    void nonDBRiderTest_shouldStartWithEmptyTable_dueToSpringRolledBackTX() {
        assertThat(userRepository.count()).isZero();
    }

}
