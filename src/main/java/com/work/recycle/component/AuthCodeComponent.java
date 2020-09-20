package com.work.recycle.component;

import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heysky.common.ApiConstants;
import com.heysky.common.util.StringUtil;
import com.heysky.messaging.sdk.HeyskySmsClient;
import com.heysky.messaging.sdk.messages.Message;
import com.heysky.messaging.sdk.response.SubmitMessageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *使用heysky sms api提交一条文字短信的例子
 */
@Slf4j
@Component
public class AuthCodeComponent {

    @Autowired
    private ObjectMapper mapper;
    public static String SM1 = "您输入的验证码是：";
    public static String SM2 = "，五分钟内有效";

    public void sentTextMsg(String phone, String auth) throws JsonProcessingException {

        // Create a client for submitting to heysky
        String SM = SM1 + auth + SM2;
        String DA = "86" + phone;


        HeyskySmsClient client = null;
        try {
            client = new HeyskySmsClient("pcfjys", "hb9VIesu");
        } catch (Exception e) {
            System.err.println("Failed to instanciate a heysky Client");
            e.printStackTrace();
            throw new RuntimeException("Failed to instanciate a heysky Client");
        }

        // Create a Text SMS Message request object ...

        try {
            SM = StringUtil.byteArr2HexStr(SM.getBytes("UnicodeBigUnmarked"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Message message = new Message(ApiConstants.COMMAND_MT_REQUEST, DA, ApiConstants.DC_UNICODE, SM, ApiConstants.SUBMITPATH);

        // Use the heysky client to submit the Text Message ...

        SubmitMessageResult result = null;
        try {
            result = client.submitMessage(message);
        } catch (Exception e) {
            System.err.println("Failed to communicate with the heysky Client");
            e.printStackTrace();
            throw new RuntimeException("Failed to communicate with the heysky Client");
        }

        log.warn("{}",mapper.writeValueAsString(result));

    }
}
