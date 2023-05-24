package tripagramex.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardPhoto is a Querydsl query type for BoardPhoto
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardPhoto extends EntityPathBase<BoardPhoto> {

    private static final long serialVersionUID = 1963388813L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardPhoto boardPhoto = new QBoardPhoto("boardPhoto");

    public final tripagramex.global.auditing.QBaseTime _super = new tripagramex.global.auditing.QBaseTime(this);

    public final QBoard board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> orders = createNumber("orders", Integer.class);

    public final StringPath photo = createString("photo");

    public QBoardPhoto(String variable) {
        this(BoardPhoto.class, forVariable(variable), INITS);
    }

    public QBoardPhoto(Path<? extends BoardPhoto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardPhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardPhoto(PathMetadata metadata, PathInits inits) {
        this(BoardPhoto.class, metadata, inits);
    }

    public QBoardPhoto(Class<? extends BoardPhoto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

