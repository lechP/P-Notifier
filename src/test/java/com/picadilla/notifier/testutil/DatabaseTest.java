package com.picadilla.notifier.testutil;

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
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring-test-config.xml")
@Transactional
public abstract class DatabaseTest {

    public static final Log log = LogFactory.getLog(DatabaseTest.class);

    @Autowired
    protected ApplicationContext ctx;
    @Autowired
    private DataSource dataSource;

    /** loads data to the database using script under provided scriptPath */
    protected void loadData(String scriptPath) {
        log.debug("Will load data from: " + scriptPath);
        Resource resource = ctx.getResource(scriptPath);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
