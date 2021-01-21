public interface IElectricMeter extends java.rmi.Remote {
    public Double getTotalKilowattHours() throws java.rmi.RemoteException;
    public Double getVolts() throws java.rmi.RemoteException;
    public Double getAmps() throws java.rmi.RemoteException;
    public Double getWatts() throws java.rmi.RemoteException;
    public Double getWattsMaxDemand() throws java.rmi.RemoteException;
    public void setDate(String date) throws java.rmi.RemoteException;
    public void setTime(String time) throws java.rmi.RemoteException;
}
