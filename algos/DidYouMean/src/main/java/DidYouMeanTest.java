import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class DidYouMeanTest {
    @Test public void testBerries() {
        DidYouMean dictionary = new DidYouMean(
            new String[] {"cherry", "pineapple", "melon", "strawberry", "raspberry"});
        assertEquals("strawberry", dictionary.findMostSimilar("strawberrry"));
        assertEquals("cherry", dictionary.findMostSimilar("berry"));
    }

    @Test public void testLanguages() {
        DidYouMean dictionary = new DidYouMean(
            new String[] {"javascript", "java", "ruby", "php", "python", "coffeescript"});
        assertEquals("java", dictionary.findMostSimilar("heaven"));
        assertEquals("javascript", dictionary.findMostSimilar("javascript"));
    }

}
