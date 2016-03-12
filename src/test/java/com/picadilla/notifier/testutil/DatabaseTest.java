package com.picadilla.notifier.testutil;

import com.picadilla.notifier.spring.CommonConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * abstract class supporting integration tests which need whole Spring context (especially connection with database)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CommonConfig.class)
@Transactional
public abstract class DatabaseTest {

    public static final Log log = LogFactory.getLog(DatabaseTest.class);

    @Autowired
    protected ApplicationContext ctx;
    @Autowired
    private DataSource dataSource;

    /**
     * Loads data to the database using script under provided scriptPath. Designed as helper method for tests.
     * Thanks to @Transactional annotation, script will be rolled back after end of each test using this method.
     * @param scriptPath path to sql script
     */
    protected void loadData(String scriptPath) {
        log.debug("Will load data from: " + scriptPath);
        Resource resource = ctx.getResource(scriptPath);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
