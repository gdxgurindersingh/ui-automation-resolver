package utils;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.File;

public class EmailUtil {
    private static final ConfigProperties config = ConfigFactory.create(ConfigProperties.class);

    public static void sendEmailReport() {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(config.smtpHost());
        email.setSmtpPort(config.smtpPort());
        email.setAuthentication(config.smtpUser(), config.smtpPassword());
        email.setSSLOnConnect(true);

        try {
            email.setFrom(config.smtpUser());
            email.setSubject("Test Execution Report");
            email.setHtmlMsg("<html><body><h1>Test Execution Report</h1><p>Attached is the latest test execution report.</p></body></html>");
            email.addTo(config.recipient());

            email.attach(new File("target/surefire-reports/emailable-report.html"));
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
