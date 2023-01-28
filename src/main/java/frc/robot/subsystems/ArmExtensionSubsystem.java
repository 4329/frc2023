package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmExtensionSubsystem extends SubsystemBase {

    private CANSparkMax extensionMotor;
    private RelativeEncoder extensionEncoder;
    private SparkMaxPIDController extensionPID;
    private double setPoint;

    public ArmExtensionSubsystem() {

        extensionMotor = new CANSparkMax(15, MotorType.kBrushless);
        extensionMotor.restoreFactoryDefaults();
        extensionPID = extensionMotor.getPIDController();
        extensionEncoder = extensionMotor.getEncoder();
        extensionMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        extensionMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        extensionMotor.setSoftLimit(SoftLimitDirection.kForward, 50);
        extensionMotor.setSoftLimit(SoftLimitDirection.kReverse, -2);
        extensionEncoder.setPosition(0);
        extensionMotor.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit);
        extensionMotor.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
        extensionPID.setP(0.1);
        extensionPID.setI(1e-4);
        extensionPID.setD(1);
        extensionPID.setIZone(0);
        extensionPID.setFF(0);
        extensionPID.setOutputRange(-1, 1);
        extensionMotor.burnFlash();

    }

    public void setExtensionLength(Double setPointDouble) {
        setPoint = setPointDouble;
        extensionPID.setReference(setPointDouble, CANSparkMax.ControlType.kPosition);

    }

    public void extend() {
        setPoint += 1.5;
        extensionPID.setReference(setPoint, CANSparkMax.ControlType.kPosition);

    }

    public void retract() {
        setPoint -= 1.5;
        extensionPID.setReference(setPoint, CANSparkMax.ControlType.kPosition);
    }

} 