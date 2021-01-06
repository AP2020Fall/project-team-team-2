package view;

import main.Main;

import java.io.IOException;
import java.util.Stack;

public class ViewHandler {
    private static final Stack<View> viewStack = new Stack<>();
    private static ViewHandler viewHandler;
    //private static Stage mainWindow;

    private ViewHandler(){

}
    /*public static void setWindow(Stage window)
    {
        mainWindow = window;
    }*/
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

    private void showView() throws IOException {
        viewStack.peek().show(Main.window);
    }

    public void pop() throws IOException {
        viewStack.pop();
        showView();

    }

    public void push(View view) throws IOException{
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

    public void mainMenuBack() throws IOException {
        View view = viewStack.peek();
        viewStack.pop();
        /*if(viewStack.peek() instanceof LoginMenu)
            viewStack.push(view);*/
        showView();
    }

}