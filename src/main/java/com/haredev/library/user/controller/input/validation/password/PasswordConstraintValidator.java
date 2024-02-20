package com.haredev.library.user.controller.input.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.passay.*;

import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator implements
        ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password password) { }

    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 15),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 2),
                new CharacterRule(EnglishCharacterData.Special, 2),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new WhitespaceRule())
        );
        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = passwordValidator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
