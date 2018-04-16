package app.manager;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

public class MailManager {

    private JavaMailSender mailSender;

    public void sendEmails(final String[] emails, final String subject, final String content) throws MailManagerException {

        if (emails == null) {
            throw new MailManagerException(EmailCustomMsg.NULL_EMAIL_LIST.toString());
        }

        if (emails.length == 0) {
            throw new MailManagerException(EmailCustomMsg.EMPTY_EMAIL_LIST.toString());
        }

        try {
            MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    mimeMessage.setFrom(new InternetAddress("from@mail.com"));

                    List<InternetAddress> toList = new ArrayList<InternetAddress>();
                    for (String email : emails) {
                        toList.add(new InternetAddress(email));
                    }
                    InternetAddress[] toArr = new InternetAddress[toList.size()];
                    toList.toArray(toArr);
                    mimeMessage.setRecipients(Message.RecipientType.TO, toArr);
                    mimeMessage.setSubject(subject);
                    mimeMessage.setText(content);
                }
            };
            mailSender.send(mimeMessagePreparator);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MailManagerException(e.getMessage(), e);
        }
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
