import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Options;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.Options.print;

/**
 * This case study uses Java object-oriented features to count the
 * number of images reachable from a recursively-defined folder
 * structure.  The root folder can either reside locally (filesystem
 * -based) or remotely (web-based).
 */
class ImageCounter {
    /**
     * A cache of unique URIs that have already been processed.
     */
    final Set<Object> mUniqueUris = new HashSet<>();

    /**
     * This static main() entry point runs the example.
     */
    public static void main(String[] args) {
        // Initializes the Options singleton.
        Options.instance().parseArgs(args);

        // Create an object that counts the images.
        new ImageCounter();
    }

    /**
     * Constructor counts all the images reachable from the root URI.
     */
    ImageCounter() {
        // Get the URI to the root of the page/folder being traversed.
        var rootUri = Options.instance().getRootUri();

        // Perform the image counting starting at the root Uri, which
        // is given an initial depth count of 1.
        int totalImages = countImages(rootUri, 1);

        print(totalImages
              + " total image(s) are reachable from "
              + rootUri);
    }

    /**
     * Main entry point into the logic for counting images
     * sequentially.
     *
     * @param pageUri The URL that we're counting at this point
     * @param depth The current depth of the recursive processing
     * @return The number of images counted at this {@code depth}
     */
    int countImages(String pageUri,
                    int depth) {
        // Return 0 if we've reached the depth limit of the crawling.
        if (depth > Options.instance().maxDepth()) {
            print("[Depth"
                  + depth
                  + "]: Exceeded max depth of "
                  + Options.instance().maxDepth());

            return 0;
        }

        // Check to see if we've already visited this URL and add the
        // new url to the hashset, so we don't try to revisit it again
        // unnecessarily.
        else if (!mUniqueUris.add(pageUri)) {
            print("[Depth"
                  + depth
                  + "]: Already processed "
                  + pageUri);

            // Return 0 if we've already examined this url.
            return 0;
        }

        // Sequentially (1) count the number of images on this page
        // and (2) crawl other links accessible via this page and
        // count their images.
        else
            return countImagesImpl(pageUri, depth);
    }

    /**
     * This helper method performs the main image counting algorithm
     * sequentially.
     *
     * @param pageUri The URL that we're counting at this point
     * @param depth The current depth of the recursive processing
     * @return The number of images counted
     */
    int countImagesImpl(String pageUri,
                        int depth) {
        try {
            // Get the HTML page at the root URI.
            var page = getStartPage(pageUri);

            // Count the # of unique images on this page.
            int imagesInPage =
                countUniqueImages(pageUri,
                                  depth,
                                  getImagesInPage(page));

            // Print the # of unique images on this page.
            print("[Depth"
                  + depth
                  + "]: "
                  + imagesInPage
                  + " unique image(s) at "
                  + pageUri);

            // Count the # of images in links on this
            // page and returns this count.
            int imagesInLinks = crawlLinksInPage(page, depth);

            // Return a count of the # of images on this page plus the
            // # of images on links accessible via this page.
            return imagesInPage + imagesInLinks;
        } catch (Exception e) {
            print("For '"
                  + pageUri
                  + "': "
                  + e.getMessage());
            // Return 0 if an exception happens.
            return 0;
        }
    }

    /**
     * @return The page at the root {@code pageUri}
     */
    Document getStartPage(String pageUri) {
        // Use the Jsoup wrapper facade to download the HTML page.
        return Options
            .instance()
            .getJSuper()
            .getPage(pageUri);
    }

    /**
     * @return A collection of IMG SRC URLs in this page
     */
    Elements getImagesInPage(Document page) {
        return page.select("img[src]");
    }

    /**
     * Count the unique images in the {@code images}.
     *
     * @param pageUri The URL that we're considering at this point
     * @param images The collection of IMG SRC URLs in this page
     * @return The number of unique images in the {@code images}
     */
    int countUniqueImages(String pageUri,
                          int depth,
                          Elements images) {
        // Remove "index.html" or "/index.html" from pageUri.
        pageUri = pageUri
            .replaceAll("/?index\\.html$", "");

        // Append "/" to pageUri if it's not empty.
        if (!pageUri.isEmpty())
            pageUri += "/";

        // Initialize the count of unique images.
        int count = 0;

        // Iterate through the collection of IMG SRC URLs and count
        // the number of unique images.
        for (Element image : images) {
            // Prepend the page URI to each image URL.
            var uri = pageUri + image.attr("src");

            // If add() returns true the image URL is unique and we
            // can count it. 
            if (mUniqueUris.add(uri))
                count++;
            else
                // Otherwise, it's a duplicate.
                print("[Depth"
                      + depth
                      + "]: Already processed " + uri);
        }

        // Return the count of unique images.
        return count;
    }

    /**
     * Recursively crawl through links that are in the {@code page}.
     *
     * @param page The {@link Document} containing HTML
     * @param depth The depth of the level of web page traversal
     * @return A count of how many images were in each link from the
     *         {@link Document}
     */
    int crawlLinksInPage(Document page,
                         int depth) {
        int imageCount = 0;

        // Iterate through the page and count the # of nested links.
        for (var link : page
                 // Find all the links on this page.
                 .select("a[href]"))

            // Count of the number of images found at that link by
            // recursively visiting all links on this page.
            imageCount += countImages(Options
                                      .instance()
                                      .getJSuper()
                                      .getHyperLink(link),
                                      depth + 1);

        // Return a count of the number of images reachable from this
        // page.
        return imageCount;
    }
}
