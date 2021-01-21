package com.mycompany.powercomms;

/**
 * @author ebruno
 */
public class CommunicationServices {
    public void sendOverPowerLines(String someMessage) {
        System.out.println("Library: sending message over power lines");
    }
    
    public String receiveOverPowerLines() {
        return "no message";
    }
}
