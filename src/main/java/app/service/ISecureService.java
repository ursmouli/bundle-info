package app.service;

import app.manager.MailManagerException;

public interface ISecureService {
    void sendNotificationEmail(final String[] emails, final String subject, final String body)
            throws MailManagerException;
}
