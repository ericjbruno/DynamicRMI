# DynamicRMI

Place your RMI classes to be exposed in the "comps/rmi" directory, and any dependant JAR files in the "comps/rmi/lib" directory. The "run" script file includes these two directories in the classpath so the class files and JAR files can be loaded via Class.forName(). If you choose to group .class file in their own individual directories, you'll need to include these directories in the Java classpath as well.

The "test-components" directory includes sample class files to be exposed via RMI dynamically. ElectricMeter uses an external JAR file, and TestComponent is just a simple example.

The "test-client" directory includes a class that uses the RMI server to calls the remote code (ElectricMeter and TestComponent). You can run this on another computer or VM, or on the same computer as the RMI server. Just update the IP addresses in RMI server and the test client to match the IP addresses on your own network. Also, update the IP address in the "rmipolicy.txt" file as well.
