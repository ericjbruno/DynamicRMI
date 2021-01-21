public class TestComponent implements ITestComponent
{
    private int count = 12;
    private String name = "Eric";
    
    public TestComponent()
    {
        //System.out.println("TestComponent created");
    }
    
    public Integer getCount()
    {
        System.out.println("TestComponent.getCount()");
        return new Integer(count);
    }
    
    public void setCount(Integer count)
    {
        System.out.println("TestComponent.setCount("+count+")");
        this.count = count.intValue();
    }
    
    public String getName()
    {
        System.out.println("TestComponent.getName()");
        return name;
    }
    
    public void setName(String name)
    {
        System.out.println("TestComponent.setName("+name+")");
        this.name = name;
    }
}
