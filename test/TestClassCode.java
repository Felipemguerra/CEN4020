import org.junit.Assert;
import org.junit.Test;

public class TestClassCode {

    public CreateUser user = new CreateUser(false);

    @Test
    public void testNotEnough() {
        Assert.assertFalse("0 should not be accepted", user.checkClassCode("10"));
    }
    @Test
    public void testEmpty() {
        Assert.assertFalse("\"\" should not be accepted", user.checkClassCode(""));
    }
    @Test
    public void testNewline() {
        Assert.assertFalse("\\n should not be accepted", user.checkClassCode("\n"));
    }
    @Test
    public void testSpace() {
        Assert.assertFalse("\" \" should not be accepted", user.checkClassCode(" "));
    }
    @Test
    public void testTooMany() {
        Assert.assertFalse("22345 should not be accepted", user.checkClassCode("22345"));
    }
    @Test
    public void testLetters() {
        Assert.assertFalse("word should not be accepted", user.checkClassCode("word"));
    }
    @Test
    public void testSpecial() {
        Assert.assertFalse("\\ should not be accepted", user.checkClassCode("\\"));
    }
    @Test
    public void testNull() {
        Assert.assertFalse("null should not be accepted", user.checkClassCode(null));
    }
}
