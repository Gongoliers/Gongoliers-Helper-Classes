package com.thegongoliers.commands;

import com.thegongoliers.subsystems.Gripper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseGripper extends Command {

	private Gripper gripper;

	public CloseGripper(Gripper gripper) {
		requires(gripper);
		this.gripper = gripper;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		gripper.close();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return gripper.isClosed();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
