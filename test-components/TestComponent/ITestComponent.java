public interface ITestComponent extends java.rmi.Remote
{
    public Integer getCount() throws java.rmi.RemoteException;
    public void setCount(Integer count) throws java.rmi.RemoteException;
    public String getName() throws java.rmi.RemoteException;
    public void setName(String name) throws java.rmi.RemoteException;
}
