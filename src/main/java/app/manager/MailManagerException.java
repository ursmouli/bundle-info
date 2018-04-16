package app.manager;

import org.springframework.mail.MailException;

public class MailManagerException extends MailException {
    public MailManagerException(final String message) {
        super(message);
    }

    public MailManagerException(final String message, final Throwable e) {
        super(message, e);
    }
}
