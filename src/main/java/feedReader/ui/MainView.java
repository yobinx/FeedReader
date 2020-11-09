package feedReader.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import feedReader.FeedDataCollector;
import feedReader.objects.FeedChannel;
import feedReader.objects.FeedEntry;


import java.util.List;


@Route
@PWA(name = "Feed Reader Application",
        shortName = "Feed Reader App",
        description = "Collect and display information from different feeds",
        enableInstallPrompt = false)
public class MainView extends VerticalLayout
{

    public MainView()
    {
        //get all feed information
        FeedDataCollector feedData = new FeedDataCollector();
        List<FeedChannel> feeds = feedData.getFeedChannelInformation();

        Text headerText = new Text("Available Channels:");

        //built grid with channel information from the list of feeds
        Grid<FeedChannel> feedGrid = new Grid<>();
        feedGrid.setItems(feeds);
        feedGrid.addColumn(FeedChannel::getTitle).setHeader("Title").setResizable(true);
        feedGrid.addColumn(FeedChannel::getDescription).setHeader("Description").setResizable(true);
        feedGrid.addColumn(FeedChannel::getPubDate).setHeader("Publication Date").setResizable(true);
        //clickable link
        feedGrid.addComponentColumn(e -> new Anchor(e.getLink(), e.getLink())).setHeader("Link").setResizable(true);
        feedGrid.setHeightByRows(true);

        //grid for channel entries
        Grid<FeedEntry> entryGrid = new Grid<>();

        //text for channel entries
        Text channelText = new Text("Entries from selected Channel:");

        //button to add entry to database
        Button addToDbButton = new Button("Add Selected Entry to Database");
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

                Notification.show("Entry was successfully added to database");
            }
        });


        //button to show the entries from the selected channel
        Button showEntriesButton = new Button("Show Channel Entries");
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


        //add all components
        add(headerText, feedGrid, showEntriesButton);
    }

}
