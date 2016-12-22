package com.thegongoliers.subsystems;

import com.thegongoliers.output.FlywheelInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Flywheel extends Subsystem implements FlywheelInterface {

	private SpeedController motor;

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
