package org.launchcode.PlatePlanner.mailing;

import  jakarta.mail.MessagingException;

public interface EmailService {

    void sendMail(final AbstractEmailContext email) throws MessagingException;

}
