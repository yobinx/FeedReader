package feedReader.feedObjects;

import java.util.LinkedList;
import java.util.List;

/* FeedChannel: store the title, description, pubDate, link and List<FeedEntry> of a feed-channel */
public class FeedChannel
{
    private final String title;
    private final String link;
    private final String description;
    private final String language;
    private final String copyright;
    private final String pubDate;

    private final List<FeedEntry> entries = new LinkedList<>();

    public FeedChannel(String title, String link, String description, String language, String copyright, String pubDate)
    {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
    }

    // Getter
    public String getTitle()
    {
        return title;
    }

    public String getLink()
    {
        return link;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getCopyright()
    {
        return copyright;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public List<FeedEntry> getEntries()
    {
        return entries;
    }

    //Adder
    public void addEntry(FeedEntry entry)
    {
        entries.add(entry);
    }


    //toString
    @Override
    public String toString()
    {
        return "FeedChannel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", copyright='" + copyright + '\'' +
                ", pubDate=" + pubDate +
                ", entries=" + entries +
                '}';
    }
}
