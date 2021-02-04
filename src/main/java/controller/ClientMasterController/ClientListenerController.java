package controller.ClientMasterController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.javatuples.Pair;
import view.TabHandler;

public class ClientListenerController {

    public String takeAction(String input) {
        Pair<String,String> instruction = new Gson().fromJson(input,new TypeToken<Pair<String,String>>(){}.getType());
        if(instruction.getValue0().equals("refresh"))
        {
            TabHandler.getTabHandler().refresh();
            return "Refresh Successful.";
        }
        return "";
    }
}
