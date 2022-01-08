package duhan.practice.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * AudiginListener로 대체한다. (너무 자주 쓰이기때문에 jpa에서 제공해준다)
 */
public class MyEntityListener {

    @PrePersist
    public void prePersist(Object o) { // 리스너는 해당 엔티티를 받아서 처리해야하기 때문에 Object를 파라미터로 꼭 받아야한다.
        if (o instanceof Auditable) {
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void PreUpdate(Object o) {
        if (o instanceof Auditable) {
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
