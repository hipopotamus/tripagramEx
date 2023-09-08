package tripagramex.domain.follow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.entity.Account;
import tripagramex.global.auditing.BaseField;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow extends BaseField {

    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Account following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Account follower;
}
