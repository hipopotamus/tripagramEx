package tripagramex.global.auditing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseField is a Querydsl query type for BaseField
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseField extends EntityPathBase<BaseField> {

    private static final long serialVersionUID = -1327706395L;

    public static final QBaseField baseField = new QBaseField("baseField");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public QBaseField(String variable) {
        super(BaseField.class, forVariable(variable));
    }

    public QBaseField(Path<? extends BaseField> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseField(PathMetadata metadata) {
        super(BaseField.class, metadata);
    }

}

