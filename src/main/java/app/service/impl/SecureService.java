package app.service.impl;

import app.service.ISecureService;
import app.manager.MailManager;
import app.manager.MailManagerException;
import org.springframework.mail.MailException;

public class SecureService implements ISecureService {
    private MailManager mailManager;
    public void sendNotificationEmail(String[] emails, String subject, String body) throws MailException {
        try {
            mailManager.sendEmails(emails, subject, body);
        } catch (Exception e) {
            throw new MailManagerException(e.getMessage());
        }
    }

    public MailManager getMailManager() {
        return mailManager;
    }

    public void setMailManager(MailManager mailManager) {
        this.mailManager = mailManager;
    }
}
