package tripagramex.domain.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.domain.account.entity.Account;
import tripagramex.domain.board.enums.Category;
import tripagramex.global.auditing.BaseField;
import tripagramex.global.jpa.converter.StringListConverter;

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

    private String thumbnail;

    private Integer view = 0;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Convert(converter = StringListConverter.class)
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<BoardTag> boardTags = new ArrayList<>();

    public void addAccountId(Long accountId) {
        account = Account.builder()
                .id(accountId)
                .build();
    }

}
