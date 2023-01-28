package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {

    private CANSparkMax armMotor;
    private RelativeEncoder armEncoder;
    private SparkMaxPIDController armPID;

    public ArmSubsystem() {

        armMotor = new CANSparkMax(12, MotorType.kBrushless);
        armMotor.restoreFactoryDefaults();
        armPID = armMotor.getPIDController();
        armEncoder = armMotor.getEncoder();
        armMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        armMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        armMotor.setSoftLimit(SoftLimitDirection.kForward, 50);
        armMotor.setSoftLimit(SoftLimitDirection.kReverse, -2);
        armEncoder.setPosition(0);
        armMotor.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit);
        armMotor.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
        armPID.setP(0.1);
        armPID.setI(1e-4);
        armPID.setD(1);
        armPID.setIZone(0);
        armPID.setFF(0);
        armPID.setOutputRange(-1, 1);
        armMotor.burnFlash();

    }

    public void setArmPosition(Double setPoint) {

        armPID.setReference(setPoint, CANSparkMax.ControlType.kPosition);
    }

    public boolean armAtSetpoint() {

        return false;
        //how do we find out whether or not the PID controller is at the setpoint, and do we need to know that?
    }

}