package duhan.practice.service;

import duhan.practice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager em;

    @Transactional
    void put() {
        User user = new User();
        user.setName("dudu");
        user.setEmail("dudu@test.com");
        em.persist(user);

        em.detach(user);
        user.setName("duduNew");

        em.merge(user);

        em.clear();
    }
}
