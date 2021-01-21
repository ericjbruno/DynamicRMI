package server.dynamicrmi;

import java.io.File;
import java.lang.ProcessHandle.Info;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class RMIServer {
    boolean rmiLogging = false;
    List<String> components = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        RMIServer main = new RMIServer();
    }
    
    public RMIServer() throws Exception {
        // Start rmiregistry and give it time to start
        //
        System.out.println("Starting 'rmiregistry'");
        ProcessBuilder builder = new ProcessBuilder("rmiregistry");
        builder.directory(new File("./comps/rmi"));
        Process process = builder.start();
        if ( ! process.isAlive() ) {
            return;
        }
        // Give rmiregistry a chance to start...
        try { Thread.sleep(500); } catch ( Exception e ) {}

        // Enable this to see additional RMI related logging on command line
        if ( rmiLogging ) {
            System.setProperty("sun.rmi.server.logLevel", "VERBOSE");
            System.setProperty("sun.rmi.server.exceptionTrace", "VERBOSE");
        }

        // Need to set the IP address or hostname, if searchable on your
        // network, in order for remote computers to find the rmi registry
        //
        String ipaddr = getNetworkAddress();
        System.setProperty("java.rmi.server.hostname", ipaddr);

        // Read the class files in the comps/rmi folder
        //
        System.out.println("Loading components:");
        components = getComponents();
 
        // Load the components, create dynamic stubs and register
        // with 'rmiregistry'
        //
        System.out.println("Creating components:");
        for ( String comp : components ) {
            exposeComponentRMI(comp);
        }
    }
    
    protected List<String> getComponents() {
        String rmiPath = "comps/rmi";

        File dir = new File( rmiPath );
        String[] files = dir.list();
        if ( files == null )
        	return null;
        
        ArrayList<String> components = new ArrayList<>();
            
        for ( String file : files ) {
            try {
                if ( file.contains(".") ) {
                    int end = file.indexOf('.');
                    String className = file.substring(0,end);
                    System.out.println("--found component " + className);
                    components.add(className);
                }
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        
    	return components;
    }
    
    protected boolean exposeComponentRMI(String classname) {
        try {
            // Make sure it's a class
            //
            Class c = Class.forName(classname);
            if ( c.isInterface() ) {
                System.out.println("--skipping " + classname + " (interface)");
                return false;
            }

            Method[] m = c.getDeclaredMethods();

            // Get all of the component's interfaces and make sure 
            // java.rmi.Remote is added if not there now
            //
            Object comp = c.getDeclaredConstructor().newInstance();
            List<Class> interfaces = new ArrayList<Class>();
            Collections.addAll(interfaces, c.getInterfaces());
            if ( ! interfaces.contains(java.rmi.Remote.class) ) {
                interfaces.add(java.rmi.Remote.class);
            }

            Class[] allInterfaces = new Class[interfaces.size()];

            // Build an RMI-enabled proxy object to wrap this component
            // and dynamically generate a stub for it
            //
            RMIProxyHandler handler = new RMIProxyHandler( comp );
            Remote proxy = 
                    (Remote)Proxy.newProxyInstance(  
                            comp.getClass().getClassLoader(), 
                            interfaces.toArray(allInterfaces), 
                            handler);

            Remote stub = (Remote)UnicastRemoteObject.exportObject(proxy,0);

            // Bind the remote object's stub in the registry
            // NOTE: rmiregistry must be running, started from the same
            // directory (comps/rmi) where the code resides to expose via RMI
            //
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(classname, (Remote)stub);
            System.out.println("--created component " + classname);
            return true;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    	return false;
    }

    protected String getNetworkAddress() {
    	String addr = "0.0.0.0";
    	try { 
            Enumeration en = NetworkInterface.getNetworkInterfaces();
            while(en.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) en.nextElement();
                if ( !n.isLoopback() && n.isUp() ) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        String temp =i.getHostAddress();
                        if ( temp.length() > 0 && !temp.contains(":") ) {
                            addr = temp; 
                            System.out.println("Local IP address: " + addr);
                        }
                    }
                }
            }
    	}
    	catch ( Exception e ) { }
    	return addr;
    }
}

