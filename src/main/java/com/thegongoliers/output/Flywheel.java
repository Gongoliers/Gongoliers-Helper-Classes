package com.thegongoliers.output;

import com.thegongoliers.output.interfaces.FlywheelInterface;

import edu.wpi.first.wpilibj.SpeedController;

public class Flywheel implements FlywheelInterface {

	private SpeedController motor;

	/**
	 * Create a flywheel using the specified SpeedController.
	 * 
	 * @param motor
	 *            The flywheel's motor.
	 */
	public Flywheel(SpeedController motor) {
		this.motor = motor;
	}

	@Override
	public void stop() {
		motor.stopMotor();
	}

	@Override
	public void spinOutward(double speed) {
		motor.set(speed);
	}

	@Override
	public void spinInward(double speed) {
		motor.set(-speed);
	}

}