package duhan.practice.repository;

import duhan.practice.domain.Book;
import duhan.practice.domain.Publisher;
import duhan.practice.domain.Review;
import duhan.practice.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void initDummies() {
        givenBookAndReview();
    }
    @Test
    void bookRelationTest() {
        userRepository.findAll().forEach(System.out::println);
        User user = userRepository.findByEmail("dudu@test.com");
        userRepository.save(user);
        System.out.println("===============");
        System.out.println(user);
        System.out.println("user = " + user);
        System.out.println("Review : " + user.getReviews());
        System.out.println("Book : " + user.getReviews().get(0).getBook());
        System.out.println("Publisher : " + user.getReviews().get(0).getBook().getPublisher());
        System.out.println("===============");
    }

    private void givenBookAndReview() {
        givenReview(givenUser(), givenBook(givenPublisher()));
    }


    private User givenUser() {
        User user = User.builder()
                .email("dudu@test.com")
                .name("dudu")
                .build();
        return userRepository.save(user);
    }

    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("jpa 도서");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    private void givenReview(User user, Book book) {
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무너무 재밌었습니다^^");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);
        reviewRepository.save(review);
    }

    private Publisher givenPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("작은서점");
        return publisherRepository.save(publisher);
    }
}