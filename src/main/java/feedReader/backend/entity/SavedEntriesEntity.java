package feedReader.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* Entity-Class: attributes for the db*/
@Entity
public class SavedEntriesEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String title = "";

    private String description = "";

    @NotEmpty
    private String pubDate = "";

    @NotEmpty
    private String link = "";


    //Getter
    public Long getId() {
        return id;
    }

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


    //Setter
    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}
