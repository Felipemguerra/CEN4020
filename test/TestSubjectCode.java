import org.junit.Assert;
import org.junit.Test;

public class TestSubjectCode {
    @Test
    public void testTooMany() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("COPY should not be accepted", user.checkClassSubject("COPY"));
    }
    @Test
    public void testNotEnough() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("GO should not be accepted", user.checkClassSubject("GO"));
    }
    @Test
    public void testDigit() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("2 should not be accepted", user.checkClassSubject("2"));
    }
    @Test
    public void testNewline() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\\n should not be accepted", user.checkClassSubject("\n"));
    }
    @Test
    public void testSpace() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\" \" should not be accepted", user.checkClassSubject(" "));
    }
    @Test
    public void testSpecial() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("* should not be accepted", user.checkClassSubject("*"));
    }
}
