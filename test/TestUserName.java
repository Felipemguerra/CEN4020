import org.junit.Assert;
import org.junit.Test;

public class TestUserName {

    public CreateUser user = new CreateUser(false);

    @Test
    public void testDigit() {
        Assert.assertFalse("0 should not be accepted", user.checkUserName("0"));
    }
    @Test
    public void testEmpty() {
        Assert.assertFalse("\"\" should not be accepted", user.checkUserName(""));
    }
    @Test
    public void testSpace() {
        Assert.assertFalse("\" \" should not be accepted", user.checkUserName(" "));
    }
    @Test
    public void testSpecial() {
        Assert.assertFalse("$ should not be accepted", user.checkUserName("$"));
    }
    @Test
    public void testNewline() {
        Assert.assertFalse("\\n should not be accepted", user.checkUserName("\n"));
    }
}
