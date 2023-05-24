package tripagramex.domain.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.global.auditing.BaseTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardTag extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "boardTag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private int orders;
}
