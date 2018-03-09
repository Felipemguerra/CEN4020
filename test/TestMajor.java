import org.junit.Assert;
import org.junit.Test;

public class TestMajor {

    public CreateUser user = new CreateUser();

    @Test
    public void testDigit() {
        Assert.assertFalse("2 should not be accepted", user.checkMajor("2"));
    }
    @Test
    public void testNull() {
        Assert.assertFalse("null should not be accepted", user.checkMajor(null));
    }
    @Test
    public void testEmpty() {
        Assert.assertFalse("\"\" should not be accepted", user.checkMajor(""));
    }
    @Test
    public void testSpace() {
        Assert.assertFalse("\" \" should not be accepted", user.checkMajor(" "));
    }
    @Test
    public void testSpecial() {
        Assert.assertFalse("% should not be accepted", user.checkMajor("%"));
    }
    @Test
    public void testNewline() {
        Assert.assertFalse("\\n should not be accepted", user.checkMajor("\n"));
    }
}
