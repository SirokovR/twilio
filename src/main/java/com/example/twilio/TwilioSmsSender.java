package com.example.twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")   //("tele2")
public class TwilioSmsSender  implements SmsSender{


    private final static Logger Logger = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final  TwilioConfig twilioConfig;

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {

        if(isPhoneNumberValid(smsRequest.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            Logger.info("Send sms " + smsRequest);

        }else {
            throw  new IllegalArgumentException("Phone number ["
                    + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        {
            if (phoneNumber.matches("\\d{11}")) return true;
                //validating phone number with -, . or spaces
            else if(phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{5}")) return true;
                //validating phone number with extension length from 3 to 5
            else //return false if nothing matches the input
                if(phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,6}")) return true;
                //validating phone number where area code is in braces ()
            else return phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{5}");

        }
    }


}
