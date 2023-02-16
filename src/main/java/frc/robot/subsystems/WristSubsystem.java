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
    
    public WristSubsystem() {

        maxValue = 30f;
        minValue = -30f;

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
    }

    public void setWristPosition(Double setpoint) {
        
        this.setpoint = setpoint;

        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
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

    @Override
    public void periodic() {

        wristMotorSetpoint.setDouble(setpoint);
        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }
    
}