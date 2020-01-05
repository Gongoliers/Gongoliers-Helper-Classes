package com.thegongoliers.input.power;

import java.util.Timer;
import java.util.TimerTask;

import com.thegongoliers.input.PDP;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.input.voltage.BatteryVoltageSensor;
import com.thegongoliers.math.GMath;

/**
 * The robot's battery
 */
public class Battery {

    private CoulombCounter coulombCounter;

    private double capacity;
    private double currentCapacity;
    private Timer timer;

    private static final long UPDATE_FREQ = 20;
    

    /**
     * Constructor
     * @param minVoltage the minimum voltage of the battery in Volts
     * @param maxVoltage the maximum voltage of the battery in Volts
     * @param capacity the total capacity of the battery in Amp-hours
     */
    public Battery(double minVoltage, double maxVoltage, double capacity){
        this.capacity = capacity;
        coulombCounter = new CoulombCounter(PDP.getInstance().getBatteryCurrentSensor(), new RobotClock());
        double soc = GMath.clamp01(GMath.inverseLerp(minVoltage, maxVoltage, new BatteryVoltageSensor().getVoltage()));
        currentCapacity = GMath.lerp(0, capacity, soc);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                update();
            }
        }, UPDATE_FREQ, UPDATE_FREQ);
    }

    /**
     * Constructor
     * @param initialCharge the current percent charge of the battery between 0 and 1
     * @param capacity the total capacity of the battery in Amp-hours
     */
    public Battery(double initialCharge, double capacity){
        this.capacity = capacity;
        coulombCounter = new CoulombCounter(PDP.getInstance().getBatteryCurrentSensor(), new RobotClock());
        currentCapacity = GMath.lerp(0, capacity, initialCharge);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                update();
            }
        }, UPDATE_FREQ, UPDATE_FREQ);
    }

    /**
     * Update the battery's capacity
     */
    private void update(){
        coulombCounter.update();
        currentCapacity -= coulombCounter.getCurrentDischarge();
        currentCapacity = GMath.clamp(currentCapacity, 0, capacity);
    }

    /**
     * Get the estimated battery percentage
     * @return the battery percentage from 0 to 100 percent
     */
    public double getBatteryPercentage(){
        return currentCapacity / capacity * 100;
    }

}