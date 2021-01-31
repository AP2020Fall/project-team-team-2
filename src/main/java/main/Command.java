package main;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Command {
    private final String command;
    private final String declaringClass;
    private final ArrayList<Object> params;
    private ClientInfo clientInfo;
    private String authToken;

    public Command(String command, String declaringClass, ArrayList<Object> params, ClientInfo clientInfo) {
        this.command = command;
        this.declaringClass = declaringClass;
        this.params = (ArrayList<Object>) params.clone();
        this.clientInfo = clientInfo;
        this.authToken = Client.getToken();
    }

    public static Command fromJson(String json) {
        return new Gson().fromJson(json, Command.class);
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<Object> getParams() {
        return params;
    }

    public String getAuthToken() {
        return authToken;
    }

    /*public String getDeclaringClass() {
            return declaringClass;
        }*/
    public Class getDeclaringClass() {
        try {
            return Class.forName(declaringClass);
        } catch (ClassNotFoundException e) {
            System.err.println("class " + declaringClass + " was not found.");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Method getMethod() {
        try {
            Class<?> c = getDeclaringClass();
            Class<?>[] paramsType = new Class[params.size()];
            for (int i = 0; i < params.size(); i++)
                paramsType[i] = params.get(i).getClass();
            return c.getDeclaredMethod(command, paramsType);
        } catch (NoSuchMethodException e) {
            System.err.println("method " + command + "@" + declaringClass + " was not found.");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Object invokeMethod() {
        try {
            Object object = getInstance();
            Method method = getMethod();
            return method.invoke(object, params.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("method " + command + "@" + declaringClass + " couldn't be invoked.");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private Object getInstance() {
        try {
            return getDeclaringClass().getConstructor(ClientInfo.class).newInstance(clientInfo);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println("couldn't make a new instance " + "@" + declaringClass);
            System.err.println(e.getMessage());
            return null;
        }
    }
}


