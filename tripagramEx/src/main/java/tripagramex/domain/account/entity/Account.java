package tripagramex.domain.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.enums.Role;
import tripagramex.domain.board.entity.Board;
import tripagramex.global.auditing.BaseField;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseField {

    @Id
    @GeneratedValue
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

    public void modify(Account account) {
        Optional.ofNullable(account.getPassword()).ifPresent(password -> this.password = password);
        Optional.ofNullable(account.getNickname()).ifPresent(nickname -> this.nickname = nickname);
        Optional.ofNullable(account.getProfile()).ifPresent(profile -> this.profile = profile);
        Optional.ofNullable(account.getIntro()).ifPresent(intro -> this.intro = intro);
    }

    public boolean canSendTempPasswordGuid() {
        if (this.tempPasswordEmailSendAt == null) {
            return true;
        }

        return this.tempPasswordEmailSendAt.isBefore(LocalDateTime.now().minusMinutes(5));
    }

    public boolean canApplyTempPassword() {
        if (this.tempPasswordAppliedAt == null) {
            return true;
        }

        return this.tempPasswordAppliedAt.isBefore(LocalDateTime.now().minusMinutes(5));
    }

    public void createTempPassword() {
        String uuid = UUID.randomUUID().toString().substring(0, 15);
        this.tempPassword = uuid.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");
    }

    public void setTempPasswordEmailSendAt() {
        this.tempPasswordEmailSendAt = LocalDateTime.now();
    }

    public void applyTempPassword(String tempPassword) {
        this.password = tempPassword;
    }
}
