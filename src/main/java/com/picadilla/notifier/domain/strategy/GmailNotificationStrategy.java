package com.picadilla.notifier.domain.strategy;

import com.picadilla.notifier.domain.common.DeliveryReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GmailNotificationStrategy implements NotificationStrategy {

    private static final Log log = LogFactory.getLog(GmailNotificationStrategy.class);

    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;
    @Value("${message.title}")
    private String messageTitle;
    @Value("${message.body}")
    private String messageBody;

    @Override
    public DeliveryReport send(String recipient) {
        SimpleMailMessage msg = composeMessage(recipient);
        try {
            mailSender.send(msg);
            log.info("Message was successfully sent to " + recipient);
            return DeliveryReport.succeed(new Date());
        } catch (MailAuthenticationException ex) {
            log.warn("Message could not be sent to " + recipient + " due to authentication issue.", ex);
            giveGmailSomeRest();
            return DeliveryReport.failed();
        } catch (MailException ex) {
            log.warn("Message could not be sent to " + recipient, ex);
            return DeliveryReport.failed();
        }
    }

    /**
     * pretty ugly workaround for gmail anti-spam protection:
     * "javax.mail.AuthenticationFailedException: 454 4.7.0 Too many login attempts, please try again later."
     */
    private void giveGmailSomeRest() {
        long twoMinutes = 120000L;
        try {
            Thread.sleep(twoMinutes);
        } catch (InterruptedException e) {
            log.error("Thread interrupted during sending by Gmail. Should not happen.", e);
            e.printStackTrace();
        }
    }

    private SimpleMailMessage composeMessage(String recipient) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(recipient);
        msg.setSubject(messageTitle);
        msg.setText(String.format(messageBody, recipient));
        return msg;
    }

}
