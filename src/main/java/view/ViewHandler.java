package view;

import main.Main;
import view.login.LoginMenu;
import view.login.WelcomeMenu;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Logger;

public class ViewHandler {
    private static final Stack<View> viewStack = new Stack<>();
    private static ViewHandler viewHandler;

    private ViewHandler() {

    }
    public static ViewHandler getViewHandler()
    {
        if(viewHandler == null)
            return viewHandler = new ViewHandler();
        else
            return viewHandler;
    }

    /*public boolean isEmpty() {
        return viewStack.isEmpty();
    }*/

    private void showView()  {
        try {
            viewStack.peek().show(Main.window);
        }catch (IOException exception)
        {
            Logger.getLogger("Show view error");//todo exit
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

    public void exit()
    {
        while(!viewStack.isEmpty())
            viewStack.pop();
        Main.window.close();
    }

    public void remove() {
        viewStack.pop();
    }

    public void mainMenuBack() {
        View view = viewStack.peek();
        viewStack.pop();
        if(viewStack.peek() instanceof LoginMenu)
            viewStack.push(view);
        showView();
    }
    public void refresh() {
        showView();
    }
    public void logout() {
        while(!(viewStack.peek() instanceof WelcomeMenu))
            viewStack.pop();
        showView();
    }
    public void exitGame()
    {
        while((viewStack.peek() instanceof RiskGameView || viewStack.peek() instanceof  StartGameView))
        {
            viewStack.pop();
            if(viewStack.isEmpty()) break;
        }
        showView();
    }
}