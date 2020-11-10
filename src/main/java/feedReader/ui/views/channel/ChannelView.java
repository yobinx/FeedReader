package feedReader.ui.views.channel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import feedReader.FeedDataCollector;
import feedReader.backend.entity.SavedEntriesEntity;
import feedReader.backend.service.SavedEntriesService;
import feedReader.feedObjects.FeedChannel;
import feedReader.feedObjects.FeedEntry;
import feedReader.ui.MainLayout;

import java.util.List;

/* The channel view: showing the available channels and their entries */
@Route(value="", layout = MainLayout.class)
@PageTitle("Feed-Channels Overview")
public class ChannelView extends VerticalLayout
{
    //grids
    private final Grid<FeedChannel> feedGrid = new Grid<>();
    private final Grid<FeedEntry> entryGrid = new Grid<>();

    //buttons
    private final Button showEntriesButton = new Button("Show Channel Entries");
    private final Button addToDbButton = new Button("Add Selected Entry to Database");

    //service for db-management
    private final SavedEntriesService savedEntriesService;

    public ChannelView(SavedEntriesService savedEntriesService)
    {
        this.savedEntriesService = savedEntriesService;

        createChannelGrid();

        setShowButtonFunctionality();
        setDbButtonFunctionality();

        H3 headerText = new H3("Available Channels:");

        //add all components
        add(headerText, feedGrid, showEntriesButton);
    }


    private void createChannelGrid(){

        //get all feed information
        FeedDataCollector feedData = new FeedDataCollector();
        List<FeedChannel> feeds = feedData.getFeedChannelInformation();

        //built grid with channel information from the list of feeds
        feedGrid.setItems(feeds);
        feedGrid.addColumn(FeedChannel::getTitle).setHeader("Title").setResizable(true);
        feedGrid.addColumn(FeedChannel::getDescription).setHeader("Description").setResizable(true);
        feedGrid.addColumn(FeedChannel::getPubDate).setHeader("Publication Date").setResizable(true);
        //clickable link
        feedGrid.addComponentColumn(e -> new Anchor(e.getLink(), e.getLink())).setHeader("Link").setResizable(true);
        //set the height of the grid to the count of existing entries
        feedGrid.setHeightByRows(true);
    }

    /* set the functionality of the show-channel-entries-button */
    private void setShowButtonFunctionality()
    {
        H3 channelText = new H3("Entries from selected Channel:");

        //button to show the entries from the selected channel
        showEntriesButton.addClickListener(buttonClickEvent ->
        {
            if (feedGrid.getSelectedItems().isEmpty())
            { //no channel is selected

                Notification.show("No Channel is selected");
            } else if (feedGrid.getSelectedItems().size() > 1)
            { //more than one channel is selected; should not be possible

                Notification.show("Please select only one Channel");
            } else
            { //exactly one channel is selected

                //get the entries from the first item in the set of selected items (there is only one)
                List<FeedEntry> channelEntries = feedGrid.getSelectedItems().iterator().next().getEntries();

                //delete the (maybe) existing grid and build a new one with the channel entries information
                entryGrid.removeAllColumns();
                entryGrid.setItems(channelEntries);

                //add the four columns
                entryGrid.addColumn(FeedEntry::getTitle).setHeader("Title").setResizable(true);
                entryGrid.addColumn(FeedEntry::getDescription).setHeader("Description").setResizable(true);
                entryGrid.addColumn(FeedEntry::getPubDate).setHeader("Publication Date").setResizable(true);
                entryGrid.addComponentColumn(e -> new Anchor(e.getLink(), e.getLink())).setHeader("Link").setResizable(true);

                //remove entryGrid (it might exist already) and add the new grid and the addToDatabase-button -> so only one entryGrid is shown at one time
                remove(channelText, entryGrid);
                add(channelText, entryGrid, addToDbButton);

            }
        });
    }


    /* set the functionality of the add-selected-entry-to-db-button */
    private void setDbButtonFunctionality()
    {
        //button to add the selected entry to database
        addToDbButton.addClickListener(buttonClickEvent ->
        {
            if (entryGrid.getSelectedItems().isEmpty())
            { //no entry is selected

                Notification.show("No Entry is selected");
            } else if (entryGrid.getSelectedItems().size() > 1)
            { //more than one entry is selected; should not be possible

                Notification.show("Please select only one Entry");
            } else
            { //exactly one entry is selected

                //new entity
                SavedEntriesEntity savedEntriesEntity = new SavedEntriesEntity();

                //selected entry
                FeedEntry selectedEntry = entryGrid.getSelectedItems().iterator().next();

                //set entity-values
                savedEntriesEntity.setTitle(selectedEntry.getTitle());
                savedEntriesEntity.setDescription(selectedEntry.getDescription());
                savedEntriesEntity.setPubDate(selectedEntry.getPubDate().toString());
                savedEntriesEntity.setLink(selectedEntry.getLink());

                //store selected entry in database
                savedEntriesService.addEntryToDb(savedEntriesEntity);

                //TODO: only store entry, if it's not already in the db

                Notification.show("Entry was successfully added to database");
            }
        });
    }
}
