package feedReader.ui.views.archive;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import feedReader.backend.entity.SavedEntriesEntity;
import feedReader.backend.service.SavedEntriesService;
import feedReader.ui.MainLayout;


/* The archive view: showing the stored entries from the database */
@Route(value="Archive", layout = MainLayout.class)
@PageTitle("Archived Entries")
public class ArchiveView extends VerticalLayout
{
    private final SavedEntriesService savedEntriesService;
    private final Grid<SavedEntriesEntity> archiveGrid = new Grid<>();
    private final Button deleteEntryFromDbButton = new Button("Delete selected entry from archive");

    public ArchiveView(SavedEntriesService savedEntriesService)
    {
        this.savedEntriesService = savedEntriesService;

        H3 headerText = new H3("Archived Entries in Database:");

        createArchiveGridColumns();
        setDeleteEntryButtonFunctionality();

        //add components
        add(headerText, archiveGrid, deleteEntryFromDbButton);

        //show all entries from the db in the grid
        archiveGrid.setItems(savedEntriesService.findAll());
    }

    /* create the four columns of the archiveGrid */
    private void createArchiveGridColumns(){
        archiveGrid.addColumn(SavedEntriesEntity::getTitle).setHeader("Title").setResizable(true);
        archiveGrid.addColumn(SavedEntriesEntity::getDescription).setHeader("Description").setResizable(true);
        archiveGrid.addColumn(SavedEntriesEntity::getPubDate).setHeader("Publication Date").setResizable(true);
        archiveGrid.addComponentColumn(e -> new Anchor(e.getLink(), e.getLink())).setHeader("Link").setResizable(true);
    }

    /* set the functionality of the delete-selected-entry-from-db-button */
    private void setDeleteEntryButtonFunctionality()
    {
        deleteEntryFromDbButton.addClickListener(buttonClickEvent ->
        {
            if (archiveGrid.getSelectedItems().isEmpty())
            { //no entry is selected

                Notification.show("No Entry is selected");
            } else if (archiveGrid.getSelectedItems().size() > 1)
            { //more than one entry is selected; should not be possible

                Notification.show("Please select only one Entry");
            } else
            { //exactly one entry is selected

                //delete selected entry
                savedEntriesService.deleteEntryFromDb(archiveGrid.getSelectedItems().iterator().next());
                Notification.show("Entry was successfully deleted from database");

                //update grid
                archiveGrid.setItems(savedEntriesService.findAll());
            }
        });
    }

}
