import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Options;

class ImageCounterTest {
    /**
     * Method under test: default or parameterless constructor of {@link ImageCounter}
     */
    @Test
    void testConstructor() {
        ImageCounter actualImageCounter = new ImageCounter();
        assertEquals("ImageCounter", actualImageCounter.TAG);
        assertEquals(1, actualImageCounter.mUniqueUris.size());
    }

    /**
     * Method under test: {@link ImageCounter#countImages(String, int)}
     */
    @Test
    void testCountImages() {
        ImageCounter imageCounter = new ImageCounter();
        assertEquals(0, imageCounter.countImages("Page Uri", 2));
        assertEquals(2, imageCounter.mUniqueUris.size());
    }

    /**
     * Method under test: {@link ImageCounter#countImages(String, int)}
     */
    @Test
    void testCountImages2() {
        assertEquals(0, (new ImageCounter()).countImages("Page Uri", 3));
    }

    /**
     * Method under test: {@link ImageCounter#countImagesImpl(String, int)}
     */
    @Test
    void testCountImagesImpl() {
        assertEquals(0, (new ImageCounter()).countImagesImpl("Page Uri", 2));
    }

    /**
     * Method under test: {@link ImageCounter#getStartPage(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetStartPage() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "utils.JSuper.getPage(String)" because the return value of "utils.Options.getJSuper()" is null
        //       at ImageCounter.getStartPage(ImageCounter.java:151)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ImageCounter()).getStartPage("Page Uri");
    }

    /**
     * Method under test: {@link ImageCounter#getImagesInPage(Document)}
     */
    @Test
    void testGetImagesInPage() {
        ImageCounter imageCounter = new ImageCounter();
        assertTrue(imageCounter.getImagesInPage(Document.createShell("Base Uri")).isEmpty());
    }

    /**
     * Method under test: {@link ImageCounter#getImagesInPage(Document)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetImagesInPage2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.jsoup.nodes.Document.select(String)" because "page" is null
        //       at ImageCounter.getImagesInPage(ImageCounter.java:159)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ImageCounter()).getImagesInPage(null);
    }

    /**
     * Method under test: {@link ImageCounter#crawlLinksInPage(Document, int)}
     */
    @Test
    void testCrawlLinksInPage() {
        ImageCounter imageCounter = new ImageCounter();
        assertEquals(0, imageCounter.crawlLinksInPage(Document.createShell("Base Uri"), 2));
    }

    /**
     * Method under test: {@link ImageCounter#crawlLinksInPage(Document, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCrawlLinksInPage2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.jsoup.nodes.Document.select(String)" because "page" is null
        //       at ImageCounter.crawlLinksInPage(ImageCounter.java:178)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ImageCounter()).crawlLinksInPage(, 2);
    }
}

