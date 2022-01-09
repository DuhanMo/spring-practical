package duhan.practice.repository;

import duhan.practice.domain.Gender;
import duhan.practice.domain.User;
import duhan.practice.domain.UserHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    void crud() {
        userRepository.save(new User());

        System.out.println(">>>" + userRepository.findAll());
    }

    @Test
    @DisplayName("Auditable, EntityListener Test")
    void listenerTest() throws InterruptedException {
        userRepository.save(User.builder().email("duhan").build());
        User user = userRepository.findByEmail("duhan");
        LocalDateTime firstCreate = user.getCreatedAt();
        LocalDateTime firstUpdate = user.getUpdatedAt();
        System.out.println(">>>" + user);

        user.setAddress("update Address");

        Thread.sleep(2000);
        userRepository.save(user);
        User duhan = userRepository.findByEmail("duhan");
        LocalDateTime lastCreate = duhan.getCreatedAt();
        LocalDateTime lastUpdate = duhan.getUpdatedAt();
        System.out.println(">>>" + duhan);

        assertAll(
                () -> assertThat(firstCreate).isEqualTo(lastCreate),
                () -> assertThat(firstUpdate).isBefore(lastUpdate)
        );
    }

    @Test
    @DisplayName("user history Test")
    void userHistoryTest() {
        User user = new User();
        user.setEmail("duhan@test.com");
        user.setName("duhan");

        userRepository.save(user);

        user.setName("duhanNew");

        userRepository.save(user);

        System.out.println("=====================");
        userHistoryRepository.findAll().forEach(System.out::println);
        System.out.println("+++++++++++++++++++_+_");
    }

    @Test
    @Transactional
    void userRelationTest() {
        User user = new User();
        user.setName("dudu");
        user.setEmail("dudu@test.com");
        user.setGender(Gender.MALE);
        userRepository.save(user);

        user.setName("duduNes");
        userRepository.save(user);

        user.setEmail("duduNew@test.com");
        userRepository.save(user);
        System.out.println("==========");

        List<UserHistory> result = userRepository.findByEmail("duduNew@test.com").getUserHistories();

        result.forEach(System.out::println);
        System.out.println("==========");


//        userRepository.findByEmail("duduNew@test.com")
//                .getUserHistories()
//                .forEach(System.out::println);

        System.out.println("userHistory.findAll().get(0).getUser() : " + userHistoryRepository.findAll().get(0).getUser());
    }
}