package server.dynamicrmi;

import java.lang.reflect.*;
import java.rmi.*;

public class RMIProxyHandler extends java.rmi.server.UnicastRemoteObject
    implements InvocationHandler, java.io.Serializable
{
    public static final long serialVersionUID = 1L;
    private transient Object comp = null;

    ///////////////////////////////////////////////////////////////////////////
    
    public RMIProxyHandler(Object comp) throws RemoteException {
        super();
        this.comp = comp;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ( comp != null ) {
            System.out.println("On server side - invoking method " + method.getName() );

            Class compClass = comp.getClass();

            // Find the correct method to invoke
            //
            Method[] methods = compClass.getMethods();
            for ( int m = 0; m < methods.length; m++ ) {
                if ( methods[m].getName().equals( method.getName() )) {
                    return methods[m].invoke(comp, args);
                }
            }
        }
        
        return null;
    }
    
    private void log(String s) {
        System.out.println(s);
    }
}
