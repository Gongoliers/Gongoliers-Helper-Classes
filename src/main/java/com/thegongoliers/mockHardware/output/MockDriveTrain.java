package com.thegongoliers.mockHardware.output;

import com.thegongoliers.output.interfaces.DriveTrainInterface;

public class MockDriveTrain implements DriveTrainInterface {

    private double rotationSpeed, forwardSpeed, leftSpeed, rightSpeed;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public double getForwardSpeed() {
        return forwardSpeed;
    }

    public double getLeftSpeed() {
        return leftSpeed;
    }

    public double getRightSpeed() {
        return rightSpeed;
    }

    @Override
    public void forward(double speed) {
        speed = limit(speed);
        this.forwardSpeed = speed;
        this.rotationSpeed = 0;
        this.leftSpeed = speed;
        this.rightSpeed = -speed;
    }

    @Override
    public void backward(double speed) {
        speed = limit(speed);
        this.forwardSpeed = -speed;
        this.rotationSpeed = 0;
        this.leftSpeed = -speed;
        this.rightSpeed = speed;
    }

    @Override
    public void rotateLeft(double speed) {
        speed = limit(speed);
        this.forwardSpeed = 0;
        this.rotationSpeed = -speed;
        this.leftSpeed = -speed;
        this.rightSpeed = -speed;
    }

    @Override
    public void rotateRight(double speed) {
        speed = limit(speed);
        this.forwardSpeed = 0;
        this.rotationSpeed = speed;
        this.leftSpeed = speed;
        this.rightSpeed = speed;
    }

    @Override
    public void arcade(double speed, double rotation) {
        speed = limit(speed);
        rotation = limit(rotation);
        this.forwardSpeed = speed;
        this.rotationSpeed = rotation;
        this.leftSpeed = 0; // TODO: calculate these
        this.rightSpeed = 0;
    }

    @Override
    public void tank(double left, double right) {
        left = limit(left);
        right = limit(right);
        this.leftSpeed = left;
        this.rightSpeed = right;
        this.forwardSpeed = 0; // TODO: calculate these
        this.rotationSpeed = 0;
    }

    @Override
    public void stop() {
        this.forwardSpeed = 0;
        this.rotationSpeed = 0;
        this.leftSpeed = 0;
        this.rightSpeed = 0;
    }

    private double limit(double speed){
        return Math.min(1, Math.max(speed, -1));
    }
}