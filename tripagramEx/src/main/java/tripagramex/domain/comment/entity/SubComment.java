package tripagramex.domain.comment.entity;

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
public class SubComment extends BaseField {

    @Id
    @GeneratedValue
    @Column(name = "subComment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String content;

    public void addSubCommentToComment(Comment comment) {
        this.comment = comment;
        if (!comment.getSubCommentList().contains(this)) {
            comment.getSubCommentList().add(this);
        }
    }
}
