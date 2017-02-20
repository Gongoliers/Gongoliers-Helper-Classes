package com.thegongoliers.output;

import com.thegongoliers.output.interfaces.IntakeInterface;

import edu.wpi.first.wpilibj.SpeedController;

public class Intake implements IntakeInterface {

	private SpeedController motor;

	/**
	 * Create an intake mechanism using the given SpeedController.
	 * 
	 * @param motor
	 *            The intake's motor.
	 */
	public Intake(SpeedController motor) {
		this.motor = motor;
	}

	@Override
	public void stop() {
		motor.stopMotor();
	}

	@Override
	public void in(double speed) {
		motor.set(speed);
	}

	@Override
	public void in() {
		in(1);
	}

	@Override
	public void out(double speed) {
		motor.set(-speed);
	}

	@Override
	public void out() {
		out(1);
	}

}