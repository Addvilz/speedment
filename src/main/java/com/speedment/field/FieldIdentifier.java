/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.field;

import com.speedment.annotation.Api;

/**
 * Identifies a particular field in an entity.
 * 
 * @author Emil Forslund
 * @since  2.3
 */
@Api(version = "2.3")
public interface FieldIdentifier {
    
    /**
     * Returns the name of the {@link Dbms} that this field is in.
     * 
     * @return  the {@link Dbms} name
     */
    String dbmsName();
    
    /**
     * Returns the name of the {@link Schema} that this field is in.
     * 
     * @return  the {@link Schema} name
     */
    String schemaName();
    
    /**
     * Returns the name of the {@link Table} that this field is in.
     * 
     * @return  the {@link Table} name
     */
    String tableName();
    
    /**
     * The unique java name of the identified column.
     * 
     * @return  the column name
     */
    String columnName();
}