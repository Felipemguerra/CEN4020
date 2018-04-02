import org.junit.Test;
import org.junit.Assert;

public class TestCreditHours {

    public CreateUser user = new CreateUser(false);

    @Test
    public void testZero() {
        Assert.assertFalse("0 should not be accepted", user.checkCreditHours("0"));
    }
    @Test
    public void testLarger() {
        Assert.assertFalse("10 should not be accepted", user.checkCreditHours("10"));
    }
    @Test
    public void testFloat() {
        Assert.assertFalse("1.5 should not be accepted", user.checkCreditHours("1.5"));
    }
    @Test
    public void testNeg() {
        Assert.assertFalse("-1 should not be accepted", user.checkCreditHours("-1"));
    }
    @Test
    public void testNegFloat() {
        Assert.assertFalse("-1.5 should not be accepted", user.checkCreditHours("-1.5"));
    }
    @Test
    public void testSpecial() {
        Assert.assertFalse("$ should not be accepted", user.checkCreditHours("$"));
    }
    @Test
    public void testEmpty() {
        Assert.assertFalse("\"\" should not be accepted", user.checkCreditHours(""));
    }
    @Test
    public void testSpace() {
        Assert.assertFalse("\" \" should not be accepted", user.checkCreditHours(" "));
    }
    @Test
    public void testNewline() {
        Assert.assertFalse("\"\\n\" should not be accepted", user.checkCreditHours("\n"));
    }
    @Test
    public void testLetter() {
        Assert.assertFalse("t should not be accepted", user.checkCreditHours("t"));
    }
    @Test
    public void testNull() {
        Assert.assertFalse("null should not be accepted", user.checkCreditHours(null));
    }
}