package tripagramex.domain.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.comment.entity.Comment;
import tripagramex.global.auditing.BaseField;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseField {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String title;

    private String content;

    private String location;

    private Integer likeCount;

    private Integer view;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "board")
    private final List<BoardTag> boardTags = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<BoardPhoto> boardPhotos = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<Comment> comments = new ArrayList<>();


}
