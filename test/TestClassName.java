import org.junit.Assert;
import org.junit.Test;

public class TestClassName {

    public CreateUser user = new CreateUser(false);

    @Test
    public void testEmpty() {
        Assert.assertFalse("\"\" should not be accepted", user.checkClassName(""));
    }
    @Test
    public void testNull() {
        Assert.assertFalse("null should not be accepted", user.checkClassName(null));
    }
    @Test
    public void testNewline() {
        Assert.assertFalse("\\n should not be accepted", user.checkClassName("\n"));
    }
}
