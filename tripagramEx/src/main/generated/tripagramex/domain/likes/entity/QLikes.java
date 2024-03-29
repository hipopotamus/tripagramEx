package tripagramex.domain.likes.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikes is a Querydsl query type for Likes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikes extends EntityPathBase<Likes> {

    private static final long serialVersionUID = -1973802651L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikes likes = new QLikes("likes");

    public final tripagramex.global.auditing.QBaseField _super = new tripagramex.global.auditing.QBaseField(this);

    public final tripagramex.domain.account.entity.QAccount account;

    public final tripagramex.domain.board.entity.QBoard board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QLikes(String variable) {
        this(Likes.class, forVariable(variable), INITS);
    }

    public QLikes(Path<? extends Likes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikes(PathMetadata metadata, PathInits inits) {
        this(Likes.class, metadata, inits);
    }

    public QLikes(Class<? extends Likes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new tripagramex.domain.account.entity.QAccount(forProperty("account")) : null;
        this.board = inits.isInitialized("board") ? new tripagramex.domain.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

