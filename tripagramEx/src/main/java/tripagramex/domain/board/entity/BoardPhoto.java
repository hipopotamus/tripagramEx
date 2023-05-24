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
public class BoardPhoto extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "boardPhoto_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String photo;

    private int orders;
}
