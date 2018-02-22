import org.junit.Assert;
import org.junit.Test;

public class TestUserName {
    @Test
    public void testDigit() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("0 should not be accepted", user.checkUserName("0"));
    }
    @Test
    public void testEmpty() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\"\" should not be accepted", user.checkUserName(""));
    }
    @Test
    public void testSpace() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\" \" should not be accepted", user.checkUserName(" "));
    }
    @Test
    public void testSpecial() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("$ should not be accepted", user.checkUserName("$"));
    }
    @Test
    public void testNewline() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\\n should not be accepted", user.checkUserName("\n"));
    }
}
