package feedReader.feedObjects;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.commons.codec.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/* FeedDataCollector: get all (feed-)urls from the properties file and store all necessary information
 * from them (title, description, pubDate, link, list<FeedEntry>) in a list of FeedChannels */
public class FeedDataCollector
{
    /* Open the feeds.properties and return all URLs from it as a list */
    public List<Object> getUrlsFromProperties()
    {
        List<Object> listOfUrls = new LinkedList<>();

        try
        {
            //open feeds.properties
            InputStream input = Resources.getInputStream("feeds.properties");
            Properties properties = new Properties();

            properties.load(input);

            //get all keys and add them to the list
            for (Object obj : properties.keySet())
            {
                listOfUrls.add(properties.get(obj));
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return listOfUrls;
    }

    /* returns the first 20 words from String input*/
    public String reduceStringToFirst20Words(String input)
    {
        int wordCount = 20;
        String[] stringArray = input.split(" ");

        if (stringArray.length <= wordCount) return input;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < wordCount; i++)
        {
            result.append(stringArray[i]);
            if (i < (wordCount - 1)) result.append(" ");
        }

        return result.toString();
    }


    /* Open the feeds (given from the URL), collect the information and add them to an object of FeedChannel */
    public List<FeedChannel> getFeedChannelInformation()
    {
        List<Object> urlList = getUrlsFromProperties();

        List<FeedChannel> feedChannels = new LinkedList<>();

        for(Object url: urlList)
        {
            try
            {
                //open the feed using the rome-framework
                URL feedUrl = new URL(url.toString());
                SyndFeedInput feedInput = new SyndFeedInput();
                SyndFeed feed = feedInput.build(new XmlReader(feedUrl));

                //collect data from the feed-channel
                String channelTitle = feed.getTitle();
                String channelLink = feed.getLink();
                String channelDescription = feed.getDescription();
                String channelLanguage = feed.getLanguage();
                String channelCopyright = feed.getCopyright();
                String channelPubDate = feed.getPublishedDate().toString();

                //store data in FeedChannel-object
                FeedChannel fc = new FeedChannel(channelTitle, channelLink, channelDescription, channelLanguage, channelCopyright, channelPubDate);

                //collect entries from feed and add them to the list inside the FeedChannel
                FeedEntry feedEntry;
                for (SyndEntry entry : feed.getEntries())
                {
                    String entryTitle = entry.getTitle();
                    SyndContent entryDescription = entry.getDescription();
                    String entryPubDate = entry.getPublishedDate().toString();
                    String entryLink = entry.getLink();

                    feedEntry = new FeedEntry(entryTitle, reduceStringToFirst20Words(entryDescription.getValue()), entryPubDate, entryLink);
                    fc.addEntry(feedEntry);
                }

                feedChannels.add(fc);

            } catch (IOException | FeedException e)
            {
                e.printStackTrace();
            }
        }

        return feedChannels;
    }

}
