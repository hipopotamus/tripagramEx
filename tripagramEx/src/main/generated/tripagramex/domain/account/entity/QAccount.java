package tripagramex.domain.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -1730309499L;

    public static final QAccount account = new QAccount("account");

    public final tripagramex.global.auditing.QBaseTime _super = new tripagramex.global.auditing.QBaseTime(this);

    public final ListPath<tripagramex.domain.board.entity.Board, tripagramex.domain.board.entity.QBoard> boards = this.<tripagramex.domain.board.entity.Board, tripagramex.domain.board.entity.QBoard>createList("boards", tripagramex.domain.board.entity.Board.class, tripagramex.domain.board.entity.QBoard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profile = createString("profile");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath tempPassword = createString("tempPassword");

    public final DateTimePath<java.time.LocalDateTime> tempPasswordAppliedAt = createDateTime("tempPasswordAppliedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> tempPasswordEmailSendAt = createDateTime("tempPasswordEmailSendAt", java.time.LocalDateTime.class);

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

