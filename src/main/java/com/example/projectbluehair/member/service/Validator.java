package com.example.projectbluehair.member.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class Validator {
    public boolean isValidMemberName(String username){
        final int MAX = 10;
        final int MIN = 4;

        // 소문자, 숫자 조합
        final String REGEX = "^[a-z0-9]{" + MIN + "," + MAX + "}$";

        return Pattern.matches(REGEX, username);
    }
    public boolean isValidPassword(String password){
        final int MAX = 15;
        final int MIN = 8;

        // 영어, 숫자, 특수문자 조합
        // ?=.* 는 [] 안의 값이 1개는 들어가야 한다는 뜻
        final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{" + MIN + "," + MAX + "}$";

        return Pattern.matches(REGEX, password);
    }
}
