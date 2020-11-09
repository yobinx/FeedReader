package feedReader.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import feedReader.ui.views.archive.ArchiveView;
import feedReader.ui.views.channel.ChannelView;

@PWA(name = "Feed Reader Application",
        shortName = "Feed Reader App",
        description = "Collect and display information from different feeds",
        enableInstallPrompt = false)

public class MainLayout extends AppLayout
{
    public MainLayout()
    {
        createLayout();
    }

    private void createLayout() {
        RouterLink channelLink = new RouterLink("Channel", ChannelView.class);
        RouterLink archiveLink = new RouterLink("Archive", ArchiveView.class);

        addToDrawer(new VerticalLayout(
                channelLink, archiveLink
        ));
    }
}
