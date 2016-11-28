package org.isheep.testutils;

import org.isheep.config.CustomSpringProfiles;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

/**
 * Created by raymo on 27/11/2016.
 */
@ActiveProfiles(profiles = {CustomSpringProfiles.TEST_PROFILE})
@RunWith(SpringRunner.class)
@org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
public abstract class DataJpaTest {

    @Inject
    private EntityManager em;

    @Inject
    private DatabaseTableList tableList;

    @Before
    @Transactional
    public void __cleanDatabase() {
        final EntityManager emCopy = em.getEntityManagerFactory().createEntityManager();

        final EntityTransaction transaction = emCopy.getTransaction();
        try {
            transaction.begin();
            emCopy.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE;").executeUpdate();
            tableList.getNames().parallelStream().forEach(table -> emCopy.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate());
            emCopy.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE;").executeUpdate();
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        } finally {
            em.clear();
        }
    }

}
