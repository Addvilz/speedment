package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function6;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4.JoinBuilder5.JoinBuilder6;
import com.speedment.runtime.join.pipeline.Pipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder6Impl<T1, T2, T3, T4, T5, T6>
    extends AbstractJoinBuilder<T6>
    implements JoinBuilder6<T1, T2, T3, T4, T5, T6> {

    JoinBuilder6Impl(AbstractJoinBuilder<?> previousStage, StageBean<T6> current) {
        super(previousStage, current);
    }

    @Override
    public JoinBuilder6<T1, T2, T3, T4, T5, T6> where(Predicate<? super T6> predicate) {
        addPredicate(predicate);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function6<T1, T2, T3, T4, T5, T6, T> constructor) {
        requireNonNull(constructor);
        final Pipeline p = pipeline();
        return streamSuppler().createJoin(
            p,
            constructor,
            (TableIdentifier<T1>) p.get(0).identifier(),
            (TableIdentifier<T2>) p.get(1).identifier(),
            (TableIdentifier<T3>) p.get(2).identifier(),
            (TableIdentifier<T4>) p.get(3).identifier(),
            (TableIdentifier<T5>) p.get(4).identifier(),
            (TableIdentifier<T6>) p.get(5).identifier()
        );
    }

}
