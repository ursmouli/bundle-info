package app.service.impl

import app.manager.EmailCustomMsg
import app.manager.MailManager
import app.manager.MailManagerException
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import javax.mail.internet.MimeMessage

import static org.mockito.Matchers.any
import static org.mockito.Matchers.anyString
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class SecureServiceSpockTest extends Specification {
    SecureService secureService

    def setup() throws MailManagerException {
        secureService = new SecureService()
    }

    def "test fail sending email"() {
        MailManager mailManager = mock(MailManager.class)
        when(mailManager.sendEmails(any(String[].class), anyString(), anyString())).thenThrow(MailManagerException.class)
        secureService.setMailManager(mailManager)
        List<String> allEmails = new ArrayList<>()
        allEmails.add("test@mail.com")

        String subject = "subject"
        String content = "content"

        String[] emails = ["test@mail.com"]

        when: 'should throw exception'
        secureService.sendNotificationEmail(emails, subject, content)

        then:
        MailManagerException ex = thrown()
    }

    def "test null and empty exception"() {
        MailManager mailManager = new MailManager()
        secureService.setMailManager(mailManager)

        when:
        secureService.sendNotificationEmail(null, "", "")

        then:
        MailManagerException ex = thrown()
        ex.message == EmailCustomMsg.NULL_EMAIL_LIST.toString()

        when:
        secureService.sendNotificationEmail("".split(), "", "")

        then:
        ex = thrown()
        ex.message == EmailCustomMsg.EMPTY_EMAIL_LIST.toString()
    }

    def "test - should not throw exception"() {
        JavaMailSender mailSender = Mock(JavaMailSender.class)
        MailManager mailManager = new MailManager()
        mailManager.setMailSender(mailSender)
        secureService.setMailManager(mailManager)

        doNothing().when(mailSender).send(any(MimeMessage.class))

        when:
        secureService.sendNotificationEmail("test@mail".split(), "", "")

        then:
        noExceptionThrown()
    }
}