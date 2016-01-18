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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.config.db.Project;
import static com.speedment.config.db.Project.CONFIG_PATH;
import static com.speedment.config.db.Project.DEFAULT_PROJECT_NAME;
import static com.speedment.config.db.Project.PACKAGE_LOCATION;
import static com.speedment.config.db.Project.PACKAGE_NAME;
import static com.speedment.config.db.trait.HasName.NAME;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.DefaultStringPropertyItem;
import com.speedment.internal.ui.property.StringPropertyItem;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectProperty extends AbstractRootDocumentProperty 
    implements Project, HasEnabledProperty, HasNameProperty {

    public ProjectProperty(Map<String, Object> data) {
        super(data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            Stream.of(new DefaultStringPropertyItem(
                    packageNameProperty(),
                    new SimpleStringProperty(DEFAULT_PACKAGE_NAME),
                    "Package Name",
                    "The name of the package to place all generated files in. This should be a fully qualified java package name."
                ),
                new DefaultStringPropertyItem(
                    packageLocationProperty(),
                    new SimpleStringProperty(DEFAULT_PACKAGE_LOCATION),
                    "Package Location",
                    "The folder to store all generated files in. This should be a relative name from the working directory."
                )
            )
        ).flatMap(s -> s);
    }
    
    public StringProperty packageNameProperty() {
        return stringPropertyOf(PACKAGE_NAME, Project.super::getPackageName);
    }

    public StringProperty packageLocationProperty() {
        return stringPropertyOf(PACKAGE_LOCATION, Project.super::getPackageLocation);
    }

    public ObjectProperty<Path> configPathProperty() {
        final ObjectProperty<Path> pathProperty = new SimpleObjectProperty<>();
        
        Bindings.bindBidirectional(
            stringPropertyOf(CONFIG_PATH, () -> null), 
            pathProperty, 
            PATH_CONVERTER
        );
        
        return pathProperty;
    }
    
    public ObservableList<DbmsProperty> dbmsesProperty() {
        return observableListOf(DBMSES, DbmsProperty::new);
    }

    @Override
    public BiFunction<Project, Map<String, Object>, DbmsProperty> dbmsConstructor() {
        return DbmsProperty::new;
    }

    @Override
    public Stream<DbmsProperty> dbmses() {
        return dbmsesProperty().stream();
    }
    
    @Override
    public DbmsProperty addNewDbms() {
        final DbmsProperty created = new DbmsProperty(this, new ConcurrentHashMap<>());
        dbmsesProperty().add(created);
        return created;
    }
    
    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case DBMSES : return new DbmsProperty(this, data);
            default     : return super.createDocument(key, data);
        }
    }
    
    private final static StringConverter<Path> PATH_CONVERTER = new StringConverter<Path>() {
        @Override
        public String toString(Path p) {
            if (p == null) return null;
            else return p.toString();
        }

        @Override
        public Path fromString(String string) {
            if (string == null) return null;
            else return Paths.get(string);
        }
    };
    
    // Must implement getName because Project does not have any parent.
    @Override
    public String getName() throws SpeedmentException {
        return getAsString(NAME).orElse(DEFAULT_PROJECT_NAME);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    } 
    
}