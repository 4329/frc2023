package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.SoftLimitDirection;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utilities.SparkFactory;

public class WristSubsystem extends SubsystemBase {

    private CANSparkMax wristMotor;
    private RelativeEncoder wristEncoder;
    private SparkMaxPIDController wristPID;
    private double setpoint;
    public GenericEntry wristMotorSetpoint;
    public GenericEntry tolerance;
    public final float maxValue;
    public final float minValue;

    private final double highAngle;
    private final double midAngle;
    private final double lowAngle;
    private final double portalAngle;
    private final double floorAngle;

    public WristAngle currentWristAngle;

    public GenericEntry[] sdjklfsadk;

    public enum WristAngle {
        
        HIGHROT,
        MIDROT,
        LOWROT,
        PORTALROT,
        FLOOR,
        ZERO
    }
    
    public WristSubsystem() {

        maxValue = 60f;
        minValue = 0f;

        highAngle = 21.99;
        midAngle = 31.2;
        lowAngle = 9.0;
        portalAngle = 35.5;
        floorAngle = 9;
        
        wristMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.wristRotate);
        wristPID = wristMotor.getPIDController();
        wristEncoder = wristMotor.getEncoder();
        wristMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        wristMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        wristMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        wristMotor.setSoftLimit(SoftLimitDirection.kForward, maxValue);
        wristMotor.setSoftLimit(SoftLimitDirection.kReverse, minValue);
        wristEncoder.setPosition(0);
        wristPID.setP(0.2);
        wristPID.setI(0);
        wristPID.setD(0);
        wristPID.setIZone(0);
        wristPID.setFF(0);
        wristPID.setOutputRange(-1, 1);
        wristPID.setSmartMotionAllowedClosedLoopError(0.3, 0);
        wristMotor.burnFlash();
        setpoint = 0;

        tolerance = Shuffleboard.getTab("setpoints").add("wrtol", 0.1).getEntry();
        wristMotorSetpoint = Shuffleboard.getTab("setpoints").add("wristMotor", 1).getEntry();
        sdjklfsadk = new GenericEntry[] {

            Shuffleboard.getTab("jdlas").add("wristhigh", 1).getEntry(),
            Shuffleboard.getTab("jdlas").add("wristmid", 1).getEntry(),
            Shuffleboard.getTab("jdlas").add("wristlow", 1).getEntry(),
            Shuffleboard.getTab("jdlas").add("wristportal", 1).getEntry(),
            Shuffleboard.getTab("jdlas").add("wristflow", 1).getEntry()
        };
    }

    public void setWristPosition(double setpoint) {
        
        this.setpoint = setpoint;

        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void setWristPosition(WristAngle wristAngle) {

        currentWristAngle = wristAngle;
        calcEnums();
    }

    public void wristUp() {

        if (setpoint > minValue) {
            setpoint -= Constants.WristConstants.wristRotationSpeed; 
            wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
        }
    }

    public void wristDown() {

        if (setpoint < maxValue) {
            setpoint += Constants.WristConstants.wristRotationSpeed; 
            wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
        }
    }

    public void wristStop() {

        wristMotor.set(0);
    }

    public boolean wristAtSetpoint() {

        if (wristEncoder.getPosition() <= setpoint + tolerance.getDouble(0) && wristEncoder.getPosition() >= setpoint - tolerance.getDouble(0)) {
         
            return true;
        } else {

            return false;
        }
    }


    private void calcEnums() {

        if (WristAngle.HIGHROT.equals(currentWristAngle)) {

            setpoint = highAngle;
        } else if (WristAngle.LOWROT.equals(currentWristAngle)) {

            setpoint = lowAngle;

        } else if (WristAngle.MIDROT.equals(currentWristAngle)) {

            setpoint = midAngle;

        } else if (WristAngle.ZERO.equals(currentWristAngle)) {

            setpoint = 0.0;
        } else if (WristAngle.PORTALROT.equals(currentWristAngle)) {

            setpoint = portalAngle;
        } else if (WristAngle.FLOOR.equals(currentWristAngle)) {

            setpoint = floorAngle;
        }
        
    }
    
    public void resetSetpoint() {

        setpoint = wristEncoder.getPosition();
    }

    @Override
    public void periodic() {

        wristMotorSetpoint.setDouble(setpoint);
        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    
}