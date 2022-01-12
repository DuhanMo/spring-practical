package duhan.practice.servicee;

import duhan.practice.domain.User;
import duhan.practice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
public class EntityManagerTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        User user = new User();
        user.setName("dudu");
        user.setEmail("dudu@test.com");
        userRepository.save(user);
    }

    @Test
    void entityManagerTest() {
        System.out.println("---------------entityManagerTest-----");
        User user = userRepository.findById(1L).get();
        user.setName("dudududududu");
        userRepository.save(user);

        System.out.println("======================");
        user.setEmail("dudududududu@test.com");
        userRepository.save(user);
        System.out.println("user = " + userRepository.findById(1L).get());
        System.out.println(userRepository.findAll());
        // @Transactional 이 없다면 2번의 update쿼리가 나간다
        // @Transactional 이 있다면 update쿼리가 날라가지 않고 영속성 컨텍스트의 데이터만 그대로 출력하고 롤백된다.
        // @Transactional 이 있다면 flush를 마지막에 했을때 update쿼리가 1번 날라간다.

        // @Transactional 있어도 findAll 을 하게 되면 영속성 컨텍스트와 비교 후 다르면 update쿼리를 한번 날려준다.
//        System.out.println(userRepository.findAll());
    }

}
