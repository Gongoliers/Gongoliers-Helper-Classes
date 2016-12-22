package com.thegongoliers.subsystems;

import com.thegongoliers.output.GripperInterface;
import com.thegongoliers.output.Solenoid;

public abstract class PneumaticGripper extends Gripper implements GripperInterface {

	private final Solenoid solenoid;

	public PneumaticGripper(Solenoid solenoid) {
		this.solenoid = solenoid;
	}

	@Override
	public void stop() {
	}

	@Override
	public void close() {
		solenoid.extend();
	}

	@Override
	public boolean isClosed() {
		return solenoid.isExtended();
	}

	@Override
	public boolean isOpened() {
		return solenoid.isRetracted();
	}

	@Override
	public void open() {
		solenoid.retract();
	}

}