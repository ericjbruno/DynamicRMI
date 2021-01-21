 
import java.rmi.*;
import java.time.Instant;
import java.util.Date;

/**
 * @author ericjbruno
 */
public class TestClientRMI {

    public static void main(String[] args) {
        try {
            String url = "";
            if ( args.length > 0 ) {
                url = "rmi://" + args[0];
            }
            else {
                url = "rmi://10.0.1.193";
            }

            ITestComponent tc = (ITestComponent)Naming.lookup(url+"/TestComponent");
            System.out.println("About to call remote object...");
            tc.setName("Eric");
            
            IElectricMeter meter = (IElectricMeter)Naming.lookup(url+"/ElectricMeter");
            System.out.println("About to call remote object...");
            meter.setDate(Date.from(Instant.now()).toString());
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}

