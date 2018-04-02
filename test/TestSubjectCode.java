import org.junit.Assert;
import org.junit.Test;

public class TestSubjectCode {

    public CreateUser user = new CreateUser(false);

    @Test
    public void testTooMany() {
        Assert.assertFalse("COPY should not be accepted", user.checkClassSubject("COPY"));
    }
    @Test
    public void testNotEnough() {
        Assert.assertFalse("GO should not be accepted", user.checkClassSubject("GO"));
    }
    @Test
    public void testDigit() {
        Assert.assertFalse("2 should not be accepted", user.checkClassSubject("2"));
    }
    @Test
    public void testNewline() {
        Assert.assertFalse("\\n should not be accepted", user.checkClassSubject("\n"));
    }
    @Test
    public void testSpace() {
        Assert.assertFalse("\" \" should not be accepted", user.checkClassSubject(" "));
    }
    @Test
    public void testSpecial() {
        Assert.assertFalse("* should not be accepted", user.checkClassSubject("*"));
    }
}
