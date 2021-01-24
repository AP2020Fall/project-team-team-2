package main;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Command {
    private final String command;
    private final String declaringClass;
    private final ArrayList<Object> params;

    public Command(String command,String declaringClass, ArrayList<Object> params) {
        this.command = command;
        this.declaringClass = declaringClass;
        this.params = (ArrayList<Object>) params.clone();

    }
    public String toJson()
    {
        return new Gson().toJson(this);
    }
    public static Command fromJson(String json)
    {
        return new Gson().fromJson(json, Command.class);
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<Object> getParams() {
        return params;
    }

    /*public String getDeclaringClass() {
        return declaringClass;
    }*/
    public Class getDeclaringClass() {
        try {
            return Class.forName(declaringClass);
        } catch (ClassNotFoundException e) {
            System.err.println("class " + declaringClass+ " was not found.");
            System.err.println(e.getMessage());
            return  null;
        }
    }
    public Method getMethod()  {
        try {
            Class<?> c = getDeclaringClass();
            Class<?>[] paramsType = new Class[params.size()];
            for(int i = 0 ; i < params.size(); i++)
                paramsType[i] = params.get(i).getClass();
            return c.getDeclaredMethod(command, paramsType);
        }catch (NoSuchMethodException e)
        {
            System.err.println("method " + command + "@" + declaringClass+ " was not found.");
            System.err.println(e.getMessage());
            return null;
        }
    }
    public Object invokeMethod()
    {
        try{
            Object object = getInstance();
            Method method = getMethod();
            System.out.println(method.invoke(object,params.toArray()));
           return method.invoke(object,params.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("method" + command + "@" + declaringClass+ "couldn't be invoked.");
            System.err.println(e.getMessage());
            return null;
        }
    }
    private Object getInstance()
    {
        try{
          return getDeclaringClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            System.err.println("couldn't make a new instance" + "@" + declaringClass);
            System.err.println(e.getMessage());
            return null;
        }
    }
}


