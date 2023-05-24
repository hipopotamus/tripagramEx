package tripagramex.domain.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.board.entity.Board;
import tripagramex.global.auditing.BaseTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String profile;

    private String intro;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String tempPassword;

    private LocalDateTime tempPasswordAppliedAt;

    private LocalDateTime tempPasswordEmailSendAt;

    @OneToMany(mappedBy = "account")
    private List<Board> boards = new ArrayList<>();


}
