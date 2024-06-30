package com.aivle.carekids.domain.user.general.service;

import com.aivle.carekids.domain.user.configuration.EmailConfig;
import com.aivle.carekids.domain.user.general.validation.SignUpValid;
import com.aivle.carekids.domain.user.utils.RedisUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

    private final SignUpValid signUpValid;
    private final EmailConfig emailConfig;
    private final RedisUtils redisUtils;
    private final TemplateEngine templateEngine;

    //인증 번호 만들기
    private String createCode() throws NoSuchAlgorithmException {
        String result;

        do {
            int num = SecureRandom.getInstanceStrong().nextInt(999999);
            result = String.valueOf(num);
        } while (result.length() != 6);

        return result;
    }

    // 이메일 내용 변경
    private String setContext(String code) {
        Context context = new Context();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        context.setVariable("code", code);

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine.process("mail", context);
    }

    //이메일 폼 생성
    @Transactional
    private MimeMessage createEmailForm(String email) throws MessagingException, NoSuchAlgorithmException {
        String authCode = createCode();

        MimeMessage message = emailConfig.javaMailSender().createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("케어Kids 가입을 위한 인증번호입니다.");
        message.setFrom(emailConfig.getUsername());
        message.setText(setContext(authCode), "utf-8", "html");

        // Redis 에 해당 인증코드 인증 시간 설정
        redisUtils.setDataExpire(email, authCode, 60 * 3L);

        return message;
    }

    //이메일로 보내기
    @Transactional
    public ResponseEntity<Map<String, String>> sendEmail(String requestEmail) throws MessagingException, NoSuchAlgorithmException {

        Map<String, String> message = signUpValid.emailValidation(requestEmail);
        if (!message.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        if (redisUtils.existData(requestEmail)) { redisUtils.deleteData(requestEmail); }
        MimeMessage sendEmail = createEmailForm(requestEmail);
        emailConfig.javaMailSender().send(sendEmail); //이메일 발송

        message.put("message", "이메일로 인증번호를 발송하였습니다");
        return ResponseEntity.ok(message);
    }

    // 이메일 검증
    public ResponseEntity<Map<String, String>> verifyEmailCode(String email, String code) {
        Map<String, String> message = new HashMap<>();
        String codeFoundByEmail = redisUtils.getData(email);
        String dataExpire = redisUtils.getDataExpire(email);

        if (codeFoundByEmail == null || !(codeFoundByEmail.equals(code))) {
            message.put("message", "잘못된 인증 번호입니다.");
            return ResponseEntity.badRequest().body(message);
        }

        if (dataExpire == null){
            message.put("message", "인증 기간이 만료되었습니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        message.put("message", "이메일 검증이 완료되었습니다.");
        return ResponseEntity.ok(message);
    }
}
