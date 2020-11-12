package feedReader.feedObjects;

import org.apache.commons.codec.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class FeedDataCollectorTest
{
    //create a channel and an entry for the tests
    private static FeedChannel channel;
    private static FeedEntry entry;

    //create an instance of the FeedDataCollector-class, in which the methods to be tested are
    private static FeedDataCollector feedDataCollector;


    /* init the global variables */
    @BeforeAll
    static void init()
    {
        channel = new FeedChannel("Test Channel Title",
                "www.this-is-a-channel.org",
                "This is a channel",
                "en-US",
                "Me",
                "Today");
        entry = new FeedEntry("Test Entry Title",
                "This is an entry",
                "Yesterday",
                "www.this-is-an-entry.org");

        feedDataCollector = new FeedDataCollector();
    }


    /* Test, if an entry is correctly added to a channel and if the values from both are also correct */
    @Test
    void entryIsSuccessfullyAddedToChannelTest(){
        //add the entry to the channel
        channel.addEntry(entry);

        //test, if the values of the channel are correctly set
        Assertions.assertEquals(channel.getTitle(), "Test Channel Title");
        Assertions.assertEquals(channel.getLink(), "www.this-is-a-channel.org");
        Assertions.assertEquals(channel.getDescription(), "This is a channel");
        Assertions.assertEquals(channel.getLanguage(), "en-US");
        Assertions.assertEquals(channel.getCopyright(), "Me");
        Assertions.assertEquals(channel.getPubDate(), "Today");

        //test, if there is only one entry in the channel
        Assertions.assertEquals(channel.getEntries().size(), 1);

        //test, if the values of the entry are correctly set
        Assertions.assertEquals(channel.getEntries().get(0).getTitle(), "Test Entry Title");
        Assertions.assertEquals(channel.getEntries().get(0).getLink(), "www.this-is-an-entry.org");
        Assertions.assertEquals(channel.getEntries().get(0).getDescription(), "This is an entry");
        Assertions.assertEquals(channel.getEntries().get(0).getPubDate(), "Yesterday");
    }


    /* Test, if the reduceStringToFirst20Words-method correctly reduce a string to only 20 words */
    @Test
    void reduceStringToFirst20WordsTest(){
        StringBuilder builder = new StringBuilder();

        //add 42-times a "Word" to a sting
        for (int i = 0; i < 42; i++) builder.append("Word ");

        //test, if the result of the method contains 20-times a "Word"
        Assertions.assertEquals(feedDataCollector.reduceStringToFirst20Words(builder.toString()),
                "Word Word Word Word Word Word Word Word Word Word Word Word Word Word Word Word Word Word Word Word");
    }


    /* Test, if the data from the properties-file is correctly read */
    @Test
    void getUrlsFromPropertiesTest() throws IOException
    {
        //read data from properties-file
        InputStream input = Resources.getInputStream("feeds.properties");
        Properties properties = new Properties();
        properties.load(input);

        //test, if the size from the properties is correct
        Assertions.assertEquals(properties.size(), 2);

        //test, if both keys are existing
        Assertions.assertTrue(properties.containsKey("seeburger"));
        Assertions.assertTrue(properties.containsKey("spiegel"));

        //test, if both values (the urls) are correct
        Assertions.assertEquals(properties.get("seeburger"), "https://www.seeburger.com/rss.xml");
        Assertions.assertEquals(properties.get("spiegel"), "https://www.spiegel.de/schlagzeilen/tops/index.rss");

    }


}
