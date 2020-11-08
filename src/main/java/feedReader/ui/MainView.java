package feedReader.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;


@Route
@PWA(name = "Feed Reader Application",
        shortName = "Feed Reader App",
        description = "Collect and display information from different feeds",
        enableInstallPrompt = false)
public class MainView extends VerticalLayout
{

    public MainView()
    {

        TextField textField = new TextField("TEST-Field");
        Text text = new Text("TEXT-Test");

        add(textField, text);
    }

}
