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
    
    public WristSubsystem() {

        wristMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.wristRotate);
        wristPID = wristMotor.getPIDController();
        wristEncoder = wristMotor.getEncoder();
        wristMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        wristMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        wristMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        wristMotor.setSoftLimit(SoftLimitDirection.kForward, -1);
        wristMotor.setSoftLimit(SoftLimitDirection.kReverse, 1);
        wristEncoder.setPosition(0);
        wristPID.setP(0.1);
        wristPID.setI(1e-4);
        wristPID.setD(1);
        wristPID.setIZone(0);
        wristPID.setFF(0);
        wristPID.setOutputRange(-1, 1);
        wristMotor.burnFlash();
        setpoint = 0;
        wristMotorSetpoint = Shuffleboard.getTab("setpoints").add("wristMotor", 1).getEntry();
    }

    public void setWristPosition(Double setpoint) {
        
        this.setpoint = setpoint;

        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }
    public void wristUp() {

        setpoint -= Constants.WristConstants.wristRotationSpeed; 
        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void wristDown() {

        setpoint += Constants.WristConstants.wristRotationSpeed; 
        wristPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void wristStop() {

        wristMotor.set(0);
    }

    public boolean wristAtSetpoint() {

        return false;
    }

    @Override
    public void periodic() {

        wristMotorSetpoint.setDouble(setpoint);
    }
    
}