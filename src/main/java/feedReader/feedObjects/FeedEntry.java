package feedReader.feedObjects;

/* FeedEntry: store the title, description, pubDate and link from an entry of a feed-channel */
public class FeedEntry
{
    private final String title;
    private final String description;
    private final String pubDate;
    private final String link;

    public FeedEntry(String title, String description, String pubDate, String link)
    {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }


    //Getter
    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public String getLink()
    {
        return link;
    }

    //toString
    @Override
    public String toString()
    {
        return "FeedEntry{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                ", link='" + link + '\'' +
                '}';
    }
}
