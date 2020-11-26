package com.example.twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class Service {
    private final SmsSender SmsSender;


    @Autowired
    public Service(@Qualifier("twilio") TwilioSmsSender twilioSmsSender) {
        this.SmsSender = twilioSmsSender;
    }
    //@Qualifier("tele2")

    public void smsSend(SmsRequest smsRequest){
        SmsSender.sendSms(smsRequest);
    }
}
