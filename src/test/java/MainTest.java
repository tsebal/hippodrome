import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Disabled
    @Test
    @Timeout(value = 21)
    void methodMainShouldBePerformedNoLongerThan22Seconds() throws Exception {
        Main.main(null);
    }
}