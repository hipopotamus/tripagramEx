package tripagramex.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.entity.Board;
import tripagramex.global.auditing.BaseField;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseField {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    private Long targetId = 0L;

    private String targetNickname = "";

    private String content;

    private String beforeDeleteContent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> subComments = new ArrayList<>();

    public void modifyContent(String content) {
        this.content = content;
    }

    public void saveContent() {
        beforeDeleteContent = content;
    }
}
