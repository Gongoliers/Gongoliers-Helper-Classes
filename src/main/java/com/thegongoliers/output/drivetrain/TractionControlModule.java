package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;
import edu.wpi.first.wpilibj.Encoder;

/**
 * A drivetrain module which work to prevent wheel slip while driving straight
 */
public class TractionControlModule extends BaseDriveModule {

    /**
     * The left encoder
     * Type: edu.wpi.first.wpilibj.Encoder
     */
    public static final String VALUE_LEFT_ENCODER = "left_encoder";

    /**
     * The right encoder
     * Type: edu.wpi.first.wpilibj.Encoder
     */
    public static final String VALUE_RIGHT_ENCODER = "right_encoder";

    /**
     * The strength of the traction control
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The minimum difference in encoder rates to consider slipping
     * Type: double
     */
    public static final String VALUE_SLIP_THRESHOLD = "slip_threshold";


    /**
     * The name of the module
     */
    public static final String NAME = "Traction Control";

    /**
     * The input threshold for turning to activate the traction control module (between 0 and 1, defaults to 0)
     * Type: double
     */
    public static final String VALUE_THRESHOLD = "threshold";

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param strength the strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param slipThreshold the maximum slip ratio to consider as slipping
     */
    public TractionControlModule(Encoder leftEncoder, Encoder rightEncoder, double strength, double slipThreshold){
        super();
        values.put(VALUE_LEFT_ENCODER, leftEncoder);
        values.put(VALUE_RIGHT_ENCODER, rightEncoder);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_SLIP_THRESHOLD, slipThreshold);
        values.put(VALUE_THRESHOLD, 0.0);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double strength = (double) getValue(VALUE_STRENGTH);
        double slipThreshold = (double) getValue(VALUE_SLIP_THRESHOLD);
        Encoder leftEncoder = (Encoder) getValue(VALUE_LEFT_ENCODER);
        Encoder rightEncoder = (Encoder) getValue(VALUE_RIGHT_ENCODER);
        double threshold = (double) getValue(VALUE_THRESHOLD);

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();


        if (Math.abs(left - right) <= threshold){
            // Trying to drive straight
            double leftRate = Math.abs(leftEncoder.getRate());
            double rightRate = Math.abs(rightEncoder.getRate());

            double robotSpeed = Math.min(leftRate, rightRate);
            if (GMath.approximately(robotSpeed, 0)) return new DriveSpeed(left, right);

            double slipRatio = Math.abs((robotSpeed - Math.max(leftRate, rightRate)) / robotSpeed);

            if (slipRatio >= slipThreshold){
                if (leftRate > rightRate){
                    // Left is slipping
                    left = decreaseSpeed(left, strength * slipRatio);
                } else {
                    // Right is slipping
                    right = decreaseSpeed(right, strength * slipRatio);
                }
            }
        }
        return new DriveSpeed(left, right);
    }

    private double decreaseSpeed(double current, double delta){
        if (current < 0){
            return Math.min(current + delta, 0);
        } else {
            return Math.max(current - delta, 0);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}