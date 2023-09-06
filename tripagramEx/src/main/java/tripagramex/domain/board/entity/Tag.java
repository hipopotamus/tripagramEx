package tripagramex.domain.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tripagramex.global.auditing.BaseField;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseField {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long tag;

    private String name;
}

