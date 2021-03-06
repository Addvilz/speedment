/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialization of {@link NonNullableExpression} that throws an exception if
 * the wrapped expression returns {@code null}.
 *
 * @param <T>     the input entity type
 * @param <INNER> the wrapped nullable expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface OrElseThrowExpression<T, INNER extends Expression<T>>
extends NonNullableExpression<T, INNER> {
    @Override
    default NullStrategy nullStrategy() {
        return NullStrategy.THROW_EXCEPTION;
    }
}