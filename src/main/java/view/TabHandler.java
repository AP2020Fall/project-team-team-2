package view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TabHandler {
    private static final ArrayList<Tab> viewArrayList = new ArrayList<>();
    private static TabHandler TabHandler;
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
    private Parent display()
    {
        try {
            return viewArrayList.get(index).show();
        }catch (IOException ignored)
        {
            Logger.getLogger("Show view error");//todo exit
            return  null;
        }
    }

    public Parent push(Tab tab)
    {

        viewArrayList.subList(index + 1,viewArrayList.size()).clear();
        viewArrayList.add(tab);
        index++;
        return display();
    }
    public Parent back()
    {
        if(index > 0) index--;
        return display();
    }
    public Parent ahead()
    {
        if(index < viewArrayList.size() - 1) index++;
        return display();
    }
}
