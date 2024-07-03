package models;

import play.data.validation.Constraints;
import play.libs.F;
import play.libs.F.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordValidator extends Constraints.Validator<String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#]).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isValid(String password) {
        return password != null && pattern.matcher(password).matches();
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new Tuple<>("error.password", new Object[]{});
    }

    public static List<String> getDetailedErrors(String password) {
        List<String> errors = new ArrayList<>();
        if (password == null) {
            errors.add("Password is required");
            return errors;
        }
        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            errors.add("Password must contain at least one uppercase letter");
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            errors.add("Password must contain at least one lowercase letter");
        }
        if (!Pattern.compile("\\d").matcher(password).find()) {
            errors.add("Password must contain at least one digit");
        }
        if (!Pattern.compile("[@$!%*?&#]").matcher(password).find()) {
            errors.add("Password must contain at least one special character (@$!%*?&#)");
        }
        return errors;
    }
}
