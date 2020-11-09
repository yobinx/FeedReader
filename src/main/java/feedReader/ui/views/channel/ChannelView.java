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
import feedReader.objects.FeedChannel;
import feedReader.objects.FeedEntry;
import feedReader.ui.MainLayout;

import java.util.List;


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


    public ChannelView()
    {
        createChannelGrid();

        createShowButtonFunctionality();
        createDbButtonFunctionality();

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
        feedGrid.setHeightByRows(true);
    }


    private void createShowButtonFunctionality()
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

                entryGrid.addColumn(FeedEntry::getTitle).setHeader("Title").setResizable(true);
                entryGrid.addColumn(FeedEntry::getDescription).setHeader("Description").setResizable(true);
                entryGrid.addColumn(FeedEntry::getPubDate).setHeader("Publication Date").setResizable(true);
                entryGrid.addComponentColumn(e -> new Anchor(e.getLink(), e.getLink())).setHeader("Link").setResizable(true);

                //remove entryGrid (it might exist already) and add the new grid and the addToDatabase-button
                remove(channelText, entryGrid);
                add(channelText, entryGrid, addToDbButton);

            }
        });
    }


    private void createDbButtonFunctionality()
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

                //TODO: add to database
                Notification.show("Entry was successfully added to database");
            }
        });
    }
}
