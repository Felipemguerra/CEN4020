import org.junit.Assert;
import org.junit.Test;

public class TestClassName {
    @Test
    public void testEmpty() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\"\" should not be accepted", user.checkClassName(""));
    }
    @Test
    public void testNull() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("null should not be accepted", user.checkClassName(null));
    }
    @Test
    public void testNewline() {
        UpdateUser user = new UpdateUser();
        Assert.assertFalse("\\n should not be accepted", user.checkClassName("\n"));
    }
}
