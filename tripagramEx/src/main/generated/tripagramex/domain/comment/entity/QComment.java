package tripagramex.domain.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -473536443L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final tripagramex.global.auditing.QBaseField _super = new tripagramex.global.auditing.QBaseField(this);

    public final tripagramex.domain.account.entity.QAccount account;

    public final StringPath beforeDeleteContent = createString("beforeDeleteContent");

    public final tripagramex.domain.board.entity.QBoard board;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<SubComment, QSubComment> subCommentList = this.<SubComment, QSubComment>createList("subCommentList", SubComment.class, QSubComment.class, PathInits.DIRECT2);

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new tripagramex.domain.account.entity.QAccount(forProperty("account")) : null;
        this.board = inits.isInitialized("board") ? new tripagramex.domain.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

