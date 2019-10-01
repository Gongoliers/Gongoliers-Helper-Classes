package com.thegongoliers.output.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.drivemodules.DriveValue;
import com.thegongoliers.output.interfaces.Drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * A wrapper class for a drivetrain which adds support for drive modules. Does not apply drive modules during tank driving.
 */
public class ModularDrivetrain implements Drivetrain {

    private Drivetrain drivetrain;
    private List<DriveModule> modules;
    private Map<DriveModule, Integer> order; 
    private DriveValue currentSpeed;
    private Clock clock;
    private double lastTime;

    /**
     * Default constructor
     * @param drivetrain the drivetrain
     */
    public ModularDrivetrain(Drivetrain drivetrain){
        this(drivetrain, new RobotClock());
    }

    /**
     * Constructor (for testing)
     * @param drivetrain the drivetrain
     * @param clock the clock to use to calculate delta time
     */
    public ModularDrivetrain(Drivetrain drivetrain, Clock clock){
        this.drivetrain = drivetrain;
        modules = new ArrayList<>();
        order = new HashMap<>();
        currentSpeed = new DriveValue(0, 0);
        this.clock = clock;
        lastTime = clock.getTime();
    }

    /**
     * Create a modular drivetrain from a differential drive
     * @param differentialDrive the differential drive
     * @return the drivetrain
     */
    public static ModularDrivetrain from(DifferentialDrive differentialDrive){
        return new ModularDrivetrain(Hardware.createDrivetrain(differentialDrive));
    }


    @Override
    public void stop() {
        drivetrain.stop();
    }

    @Override
    public void arcade(double speed, double turn) {
        DriveValue desiredSpeed = new DriveValue(speed, turn);
        double time = clock.getTime();
        double dt = time - lastTime;

        for(DriveModule module : modules){
            desiredSpeed = module.run(currentSpeed, desiredSpeed, dt);
        }
        
        currentSpeed = desiredSpeed;
        lastTime = time;
        drivetrain.arcade(currentSpeed.getForwardSpeed(), currentSpeed.getTurnSpeed());
    }

    @Override
    public void tank(double leftSpeed, double rightSpeed) {
        drivetrain.tank(leftSpeed, rightSpeed);
    }

    /**
     * Add a module to the drivetrain.
     * Note: you can add a module more than once, though this may produce undesired behavior
     * @param module the module to add
     * @param order the order to sort this module, lower order modules will be run before higher order modules
     */
    public void addModule(DriveModule module, int order){
        if (module == null) return;
        modules.add(module);
        this.order.put(module, order);
        modules.sort(Comparator.comparingInt(m -> this.order.get(m)));
    }

    /**
     * Add a module to the drivetrain, using the drivemodule's default order. 
     * Note: you can add a module more than once, though this may produce undesired behavior
     * @param module the module to add
     */
    public void addModule(DriveModule module){
        addModule(module, module.getOrder());
    }

    /**
     * Remove a module from the drivetrain
     * @param module the module to remove
     */
    public void removeModule(DriveModule module){
        modules.remove(module);
        order.remove(module);
    }

    public List<DriveModule> getInstalledModules(){
        return modules;
    }

    
}