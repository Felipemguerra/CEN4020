import org.junit.Assert;
import org.junit.Test;

public class TestClassCode {
    @Test
    public void testNotEnough() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("0 should not be accepted", user.checkClassCode("10"));
    }
    @Test
    public void testEmpty() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\"\" should not be accepted", user.checkClassCode(""));
    }
    @Test
    public void testNewline() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\\n should not be accepted", user.checkClassCode("\n"));
    }
    @Test
    public void testSpace() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\" \" should not be accepted", user.checkClassCode(" "));
    }
    @Test
    public void testTooMany() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("22345 should not be accepted", user.checkClassCode("22345"));
    }
    @Test
    public void testLetters() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("word should not be accepted", user.checkClassCode("word"));
    }
    @Test
    public void testSpecial() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\\ should not be accepted", user.checkClassCode("\\"));
    }
    @Test
    public void testNull() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("null should not be accepted", user.checkClassCode(null));
    }
}
