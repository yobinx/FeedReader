package feedReader.ui.views.archive;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import feedReader.ui.MainLayout;

@Route(value="Archive", layout = MainLayout.class)
@PageTitle("Archived Entries")

public class ArchiveView extends VerticalLayout
{
    public ArchiveView()
    {
        H3 headerText = new H3("Archived Entries in Database:");

        //TODO: add Db

        add(headerText);
    }

}
