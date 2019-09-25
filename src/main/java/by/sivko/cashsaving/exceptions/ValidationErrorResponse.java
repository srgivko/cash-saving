package by.sivko.cashsaving.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public void addViolation(String fieldName, String message) {
        violations.add(new Violation(fieldName, message));
    }

    @AllArgsConstructor
    private class Violation {

        private final String fieldName;

        private final String message;
    }
}
