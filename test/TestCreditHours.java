import org.junit.Test;
import org.junit.Assert;

public class TestCreditHours {
    @Test
    public void testZero() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("0 should not be accepted", user.checkCreditHours("0"));
    }
    @Test
    public void testLarger() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("10 should not be accepted", user.checkCreditHours("10"));
    }
    @Test
    public void testFloat() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("1.5 should not be accepted", user.checkCreditHours("1.5"));
    }
    @Test
    public void testNeg() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("-1 should not be accepted", user.checkCreditHours("-1"));
    }
    @Test
    public void testNegFloat() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("-1.5 should not be accepted", user.checkCreditHours("-1.5"));
    }
    @Test
    public void testSpecial() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("$ should not be accepted", user.checkCreditHours("$"));
    }
    @Test
    public void testEmpty() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\"\" should not be accepted", user.checkCreditHours(""));
    }
    @Test
    public void testSpace() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\" \" should not be accepted", user.checkCreditHours(" "));
    }
    @Test
    public void testNewline() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\"\\n\" should not be accepted", user.checkCreditHours("\n"));
    }
    @Test
    public void testLetter() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("t should not be accepted", user.checkCreditHours("t"));
    }
    @Test
    public void testNull() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("null should not be accepted", user.checkCreditHours(null));
    }
}