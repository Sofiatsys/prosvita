package com.team.prosvita.email;

public interface IEmailSender {

    void sendEmailConfirmation(String to, String email);
    void sendPasswordReset(String to, String email);

}
