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
        if(instruction.getValue0().equals("refresh"))
        {
            Platform.runLater(()->TabHandler.getTabHandler().refresh());
            return "Refresh Successful.";
        }
        else if(instruction.getValue0().equals("notify"))
        {

            String[] alertContent = instruction.getValue1().split("#");

            Platform.runLater(() -> AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                    TabHandler.getTabHandler().getStackRoot().getChildren().get(0),"Okay",
                    alertContent[0],alertContent[1]));
            return "Notify Successful.";
        }
        return "";
    }
}
