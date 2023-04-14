package tripagramex.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
