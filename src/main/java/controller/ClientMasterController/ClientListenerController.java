package controller.ClientMasterController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import main.Client;
import main.ClientConnector;
import org.javatuples.Pair;
import view.AlertMaker;
import view.TabHandler;

public class ClientListenerController {

    public String takeAction(String input) {
        Pair<String,String> instruction = new Gson().fromJson(input,new TypeToken<Pair<String,String>>(){}.getType());
        System.out.println("----");
        System.out.println(instruction);
        if(instruction.getValue0().equals("refresh"))
        {
            Platform.runLater(()->TabHandler.getTabHandler().refresh());
            return "Refresh Successful.";
        }
        else if(instruction.getValue0().equals("notify"))
        {
            System.out.println("hello");
            String[] alertContent = instruction.getValue1().split("#");
            System.out.println("why");
            System.out.println(alertContent[0]);
            System.out.println(alertContent[1]);
            System.out.println("whuyyyy");
            Platform.runLater(() -> AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                    TabHandler.getTabHandler().getStackRoot().getChildren().get(0),"Okay",
                    alertContent[0],alertContent[1]));
            System.out.println("herherh");
            return "Notify Successful.";
        }
        return "";
    }
}
