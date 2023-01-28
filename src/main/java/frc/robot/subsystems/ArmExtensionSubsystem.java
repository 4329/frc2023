package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmExtensionSubsystem extends SubsystemBase {

    private CANSparkMax extensionMotor;
    private RelativeEncoder extensionEncoder;
    private SparkMaxPIDController extensionPID;
    private double setpoint;

    public ArmExtensionSubsystem() {

        extensionMotor = new CANSparkMax(Constants.CANIDConstants.armExtension, MotorType.kBrushless);
        extensionMotor.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
        extensionPID = extensionMotor.getPIDController();
        extensionPID.setP(0.1);
        extensionPID.setI(1e-4);
        extensionPID.setD(1);
        extensionPID.setIZone(0);
        extensionPID.setFF(0);
        extensionPID.setOutputRange(-1, 1);
        extensionMotor.burnFlash();

    }

    public void setExtensionLength(Double setPointDouble) {
        setpoint = setPointDouble;
        extensionPID.setReference(setPointDouble, CANSparkMax.ControlType.kPosition);

    }

    public void extend(double extendAmount) {
        setpoint += extendAmount;
        extensionPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void retract(double retractAmount) {

        setpoint -= retractAmount;
        extensionPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

}
