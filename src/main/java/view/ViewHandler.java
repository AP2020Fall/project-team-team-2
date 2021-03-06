package view;

import javafx.stage.Stage;
import main.Main;
import model.RiskGame;
import view.login.LoginMenu;
import view.login.WelcomeMenu;
import view.risk.RiskGameView;
import view.risk.StartGameView;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Logger;

public class ViewHandler {
    private static final Stack<View> viewStack = new Stack<>();
    private static ViewHandler viewHandler;
    private Stage window;

    private ViewHandler() {

    }

    public static ViewHandler getViewHandler() {
        if (viewHandler == null)
            return viewHandler = new ViewHandler();
        else
            return viewHandler;
    }

    /*public boolean isEmpty() {
        return viewStack.isEmpty();
    }*/
    public void setWindow(Stage window) {
        this.window = window;
    }

    private void showView() {
        try {
            viewStack.peek().show(window);
        } catch (IOException exception) {
            System.err.println("Show view error");//todo exit
        }
    }

    public void pop() {
        viewStack.pop();
        showView();

    }

    public void push(View view) {
        viewStack.push(view);
        showView();
    }

    public void exit() {
        while (!viewStack.isEmpty())
            viewStack.pop();
        window.close();
    }

    public void remove() {
        viewStack.pop();
    }

    public void mainMenuBack() {
        View view = viewStack.peek();
        viewStack.pop();
        if (viewStack.peek() instanceof LoginMenu)
            viewStack.push(view);
        showView();
    }

    public void refresh() {
        showView();
    }

    public void logout() {
        while (!(viewStack.peek() instanceof WelcomeMenu))
            viewStack.pop();
        showView();
    }

    public void exitGame() {
        while ((viewStack.peek() instanceof RiskGameView || viewStack.peek() instanceof StartGameView)) {
            viewStack.pop();
            if (viewStack.isEmpty()) break;
        }
        showView();
    }
    public void updateMapView(){
        if(viewStack.peek() instanceof RiskGameView){
            RiskGameView newView =(RiskGameView) viewStack.peek();
            newView.updateMap();
        }
    }
    public void updateTimeView(){
        if(viewStack.peek() instanceof RiskGameView){
            RiskGameView newView =(RiskGameView) viewStack.peek();
            newView.updateCurrentTimestamp();
        }
    }
}