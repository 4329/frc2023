package frc.robot.utilities;

public class Config {

    private int frontLeftDriveMotorPort = 3;
    private int frontRightDriveMotorPort = 1;
    private int backLeftDriveMotorPort = 5;
    private int backRightDriveMotorPort = 7;

    // Rotation Motor IDs -DO NOT CHANGE-
    private int frontLeftTurningMotorPort = 4;
    private int frontRightTurningMotorPort = 2;
    private int backLeftTurningMotorPort = 6;
    private int backRightTurningMotorPort = 8;

    // Encoder IDs -DO NOT CHANGE-
    private int frontLeftTurningEncoderPort = 1;
    private int frontRightTurningEncoderPort = 0;
    private int backLeftTurningEncoderPort = 2;
    private int backRightTurningEncoderPort = 3;

    // Motor Offsets in Radians
    private double frontLeftOffset = 2.25;
    private double frontRightOffset = -0.65;
    private double backLeftOffset = -0.039;
    private double backRightOffset = 1.03;

    private boolean extraShuffleBoardToggle = true;

    private int rollDirection = 1;

    private String limelighturl = "http://10.43.29.11:5800";

    public String getLimelighturl() {
        return limelighturl;
    }

    public int getFrontLeftDriveMotorPort() {
        return frontLeftDriveMotorPort;
    }

    public int getFrontRightDriveMotorPort() {
        return frontRightDriveMotorPort;
    }

    public int getBackLeftDriveMotorPort() {
        return backLeftDriveMotorPort;
    }

    public int getBackRightDriveMotorPort() {
        return backRightDriveMotorPort;
    }
    public int getFrontLeftTurningMotorPort() {
        return frontLeftTurningMotorPort;
    }

    public int getFrontRightTurningMotorPort() {
        return frontRightTurningMotorPort;
    }

    public int getBackLeftTurningMotorPort() {
        return backLeftTurningMotorPort;
    }

    public int getBackRightTurningMotorPort() {
        return backRightTurningMotorPort;
    }

    public int getFrontLeftTurningEncoderPort() {
        return frontLeftTurningEncoderPort;
    }

    public int getFrontRightTurningEncoderPort() {
        return frontRightTurningEncoderPort;
    }

    public int getBackLeftTurningEncoderPort() {
        return backLeftTurningEncoderPort;
    }

    public int getBackRightTurningEncoderPort() {
        return backRightTurningEncoderPort;
    }

    public double getFrontLeftOffset() {
        return frontLeftOffset;
    }

    public double getFrontRightOffset() {
        return frontRightOffset;
    }

    public double getBackLeftOffset() {
        return backLeftOffset;
    }

    public double getBackRightOffset() {
        return backRightOffset;
    }

    public boolean isExtraShuffleBoardToggle() {
        return extraShuffleBoardToggle;
    }

    public int getRollDirection() {
        return rollDirection;
    }

}
