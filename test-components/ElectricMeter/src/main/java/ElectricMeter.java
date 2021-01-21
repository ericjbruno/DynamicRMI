import com.mycompany.powercomms.*;

public class ElectricMeter implements IElectricMeter {
    private CommunicationServices comms = new CommunicationServices();
    
    private String date;
    private String time;
    private Double totalKilowattHours = 10.0;
    private Double volts = 1.0;
    private Double amps = 1.0;
    private Double watts = 1.0;
    private Double wattsMaxDemand = 1001.1;
    
    public ElectricMeter() {
        System.out.println("ElectricMeter created");
    }
    
    public static void main(String[] args) throws Exception {
        ElectricMeter em = new ElectricMeter();
    }
    
    public Double getTotalKilowattHours() {
        System.out.println("ElectricMeter.getTotalKilowattHours() called");
        return Double.valueOf( totalKilowattHours += 1.1 );
    }
    
    public Double getVolts() {
        System.out.println("ElectricMeter.getVolts() called");
        return Double.valueOf( volts += 0.1);
    }

    public Double getAmps() {
        System.out.println("ElectricMeter.getAmps() called");
        return Double.valueOf( amps += 0.1);
    }

    public Double getWatts() {
        System.out.println("ElectricMeter.getWatts() called");
        return Double.valueOf( watts += 0.1);
    }

    public Double getWattsMaxDemand() {
        System.out.println("ElectricMeter.getWattsMaxDemand() called");
        return Double.valueOf(wattsMaxDemand);
    }

    public void setDate(String date) {
        System.out.println("ElectricMeter.setDate() called");
    }

    public void setTime(String time) {
        System.out.println("ElectricMeter.setTime() called");
    }
}
