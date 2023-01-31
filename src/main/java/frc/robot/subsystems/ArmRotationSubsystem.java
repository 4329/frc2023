package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmRotationSubsystem extends SubsystemBase {

    private CANSparkMax armMotor1;
    private CANSparkMax armMotor2;
    private RelativeEncoder armEncoder;
    private SparkMaxPIDController armPID;

    public ArmRotationSubsystem() {

        armMotor1 = new CANSparkMax(Constants.CANIDConstants.armRotation1, MotorType.kBrushless);
        armMotor2 = new CANSparkMax(Constants.CANIDConstants.armRotation2, MotorType.kBrushless);
        armMotor1.restoreFactoryDefaults();
        armMotor2.restoreFactoryDefaults();
        armMotor2.follow(armMotor1);
        armPID = armMotor1.getPIDController();
        armEncoder = armMotor1.getEncoder();
        armMotor1.enableSoftLimit(SoftLimitDirection.kForward, true);
        armMotor1.enableSoftLimit(SoftLimitDirection.kReverse, true);
        armMotor1.setSoftLimit(SoftLimitDirection.kForward, 50);
        armMotor1.setSoftLimit(SoftLimitDirection.kReverse, -2);
        armEncoder.setPosition(0);
        armMotor1.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit);
        armMotor1.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
        armPID.setP(0.1);
        armPID.setI(1e-4);
        armPID.setD(1);
        armPID.setIZone(0);
        armPID.setFF(0);
        armPID.setOutputRange(-1, 1);
        armMotor1.burnFlash();
        armMotor2.burnFlash();

    }

    public void setArmPosition(Double setPoint) {

        armPID.setReference(setPoint, CANSparkMax.ControlType.kPosition);
    }

    public boolean armAtSetpoint() {

        return false;
    }

}