import org.junit.Assert;
import org.junit.Test;

public class TestMajor {
    @Test
    public void testDigit() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("2 should not be accepted", user.checkMajor("2"));
    }
    @Test
    public void testNull() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("null should not be accepted", user.checkMajor(null));
    }
    @Test
    public void testEmpty() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\"\" should not be accepted", user.checkMajor(""));
    }
    @Test
    public void testSpace() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\" \" should not be accepted", user.checkMajor(" "));
    }
    @Test
    public void testSpecial() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("% should not be accepted", user.checkMajor("%"));
    }
    @Test
    public void testNewline() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\\n should not be accepted", user.checkMajor("\n"));
    }
}
