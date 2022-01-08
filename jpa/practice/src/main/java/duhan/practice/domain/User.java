package duhan.practice.domain;

import duhan.practice.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@EntityListeners(UserEntityListener.class)
//public class User implements Auditable{
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
}
