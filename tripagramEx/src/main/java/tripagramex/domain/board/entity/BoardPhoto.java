package tripagramex.domain.board.entity;

import tripagramex.global.auditing.BaseField;

//@Entity
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class BoardPhoto extends BaseField {

//    @Id
//    @GeneratedValue
//    @Column(name = "boardPhoto_id")
    Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
    private Board board;

    private String photo;

    private int orders;
}
