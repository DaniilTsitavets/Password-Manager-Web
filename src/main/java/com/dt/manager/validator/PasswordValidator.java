package com.dt.manager.validator;

import lombok.Getter;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Getter
@Component
public class PasswordValidator extends org.passay.PasswordValidator {
    public PasswordValidator() {
        super(Arrays.asList(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new CharacterRule(EnglishCharacterData.Alphabetical, 4)
        ));
    }
}
