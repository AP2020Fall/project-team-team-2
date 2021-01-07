package view;

import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TabHandler {
    private static final ArrayList<Tab> viewArrayList = new ArrayList<>();
    private static TabHandler TabHandler;
    private BorderPane borderPane;
    private static int index;
    private TabHandler() {
        index = -1;
    }
    public static TabHandler getTabHandler()
    {
        if(TabHandler == null)
            return TabHandler = new TabHandler();
        else
            return TabHandler;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    private void display()
    {
        try {
            borderPane.setCenter( viewArrayList.get(index).show());
        }catch (IOException ignored)
        {
            Logger.getLogger("Show view error");//todo exit
        }
    }

    public void push(Tab tab)
    {

        viewArrayList.subList(index + 1,viewArrayList.size()).clear();
        viewArrayList.add(tab);
        index++;
        display();
    }
    public void back()
    {
        if(index > 0) index--;
         display();
    }
    public void ahead()
    {
        if(index < viewArrayList.size() - 1) index++;
         display();
    }

    public void refresh() {
        display();
    }
}
