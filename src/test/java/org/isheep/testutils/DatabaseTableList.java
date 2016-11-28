package org.isheep.testutils;

import com.google.common.base.Strings;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.Joinable;
import org.isheep.testconfig.condition.TestProfileCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by raymo on 27/11/2016.
 */
@Component
@Conditional(value = {TestProfileCondition.class})
public class DatabaseTableList {

    @Inject
    private EntityManagerFactory emf;

    private final Set<String> names = new HashSet<>();

    public DatabaseTableList() {
    }

    @PostConstruct
    public void populateTableList() {
        final SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);

        final Map<String, ClassMetadata> classMetadataMap = sessionFactory.getAllClassMetadata();
        for (final ClassMetadata classMetadata : classMetadataMap.values()) {
            final Joinable aep = (AbstractEntityPersister) classMetadata;
            final String tableName = aep.getTableName();
            if (Strings.isNullOrEmpty(tableName) || tableName.contains(" ")) {
                continue;
            }
            names.add(tableName);
        }
    }

    public Collection<String> getNames() {
        return names;
    }

}
