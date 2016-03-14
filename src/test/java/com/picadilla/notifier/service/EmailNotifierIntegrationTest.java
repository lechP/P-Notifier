package com.picadilla.notifier.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Before;

public class EmailNotifierIntegrationTest {

    private GreenMail greenMail;
    private static final int port = 3025;
    private static final String protocol = "smtp";


    @Before
    public void setUp() throws Exception {
        greenMail = new GreenMail(new ServerSetup(port, null, protocol));
        greenMail.start();
    }

    /* TODO implement these test scenarios:

    1. delay=0 days, 2 calls to service, 1 record with NONE in db -> should result in
        a) 2xSENT and 1xNONE in db
        b) 2 emails captured by greenMail
    2. delay=1 days, 2 calls to service, 1 record with NONE in db -> should result in
        a) 1xSENT and 1xNONE in db
        b) 1 email captured by greenMail
    3. delay=5 days, 2 calls to service, 1 record with SENT, 1 record with fresh date -> should result in
        same state of db, zero emails


        Scheduler unit test -> is scheduling as it should (how many calls to Notifier in period of time)
        problem: how to kill process after some fixed time
     */

}
