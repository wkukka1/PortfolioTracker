package entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordValidatorServiceTest {
    private PasswordValidatorService passwordValidatorService;

    @Before
    public void setup() {
        this.passwordValidatorService = new PasswordValidatorService();
    }

    @Test
    public void testValidPasswordIsValid() {
        assertTrue(passwordValidatorService.passwordIsValid("asdasd"));
    }

    @Test
    public void testNullPasswordIsInvalid() {
        assertFalse(passwordValidatorService.passwordIsValid(null));
    }

    @Test
    public void testLessThan5CharsPasswordIsInvalid() {
        assertFalse(passwordValidatorService.passwordIsValid("a"));
    }
}
