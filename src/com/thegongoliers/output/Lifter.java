package com.thegongoliers.output;

public interface Lifter extends Stoppable {
	void up(double speed);

	void down(double speed);

	boolean isAtBottom();

	boolean isAtTop();

	double getPosition();
}
