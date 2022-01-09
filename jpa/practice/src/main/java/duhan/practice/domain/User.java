package duhan.practice.domain;

import duhan.practice.listener.UserEntityListener;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@EntityListeners(UserEntityListener.class)
@RequiredArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NonNull
    private String email;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @OneToMany
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    // 연관관계 메소드
    public void addReview(Review review) {
        List<Review> reviews = getReviews();
        reviews.add(review);
    }

    public List<Review> getReviews() {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        return reviews;
    }
}
