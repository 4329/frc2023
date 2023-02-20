package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utilities.SparkFactory;

public class ArmExtensionSubsystem extends SubsystemBase {

    private CANSparkMax extensionMotor;
    private RelativeEncoder extensionEncoder;
    private SparkMaxPIDController extensionPID;
    private double setpoint;
    public GenericEntry extensionMotorSetpoint;
    public GenericEntry tolerance;

    public final double highExtend;

    public final float maxValue;
    public final float minValue;

    public enum ExtendLength {

        RETRACTFULL,
        EXTENDFULL,
        ZERO
    }

    public ExtendLength currentExtendLength;

    public ArmExtensionSubsystem() {

        highExtend = 120;

        maxValue = 140f;
        minValue = -55f; //it's a float - Matthew

        extensionMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armExtension);
        extensionPID = extensionMotor.getPIDController();
        extensionEncoder = extensionMotor.getEncoder();
        extensionMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        extensionMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        extensionMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        extensionMotor.setSoftLimit(SoftLimitDirection.kForward, maxValue);
        extensionMotor.setSoftLimit(SoftLimitDirection.kReverse, minValue);
        extensionEncoder.setPosition(0);
        extensionMotor.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit);
        extensionMotor.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
        extensionPID = extensionMotor.getPIDController();
        extensionPID.setP(4);
        extensionPID.setI(1e-4);
        extensionPID.setD(1);
        extensionPID.setIZone(0);
        extensionPID.setFF(0);
        extensionPID.setOutputRange(-1, 1);
        extensionMotor.burnFlash();

        tolerance = Shuffleboard.getTab("setpoints").add("armex tolerance", 0.3).getEntry();
        extensionMotorSetpoint = Shuffleboard.getTab("setpoints").add("Arm Extension Motor", 1).getEntry();
    }

    public void setExtensionLength(double setpoint) {

        this.setpoint = setpoint;
    }

    public void setExtensionLength(ExtendLength extendLength) {

        currentExtendLength = extendLength;
        calcEnums();
    }

    public void extend() {

        if (setpoint < maxValue) {
            setpoint += Constants.ArmExtendConstants.armExtendSpeed;
        }
    }

    public void retract() {

        if (setpoint > minValue) {
            setpoint -= Constants.ArmExtendConstants.armExtendSpeed;
        }
    }

    public void resetSetpoint() {

        setpoint = extensionEncoder.getPosition();
    }

    public boolean extendAtSetpoint() {

        if (extensionEncoder.getPosition() <= setpoint + tolerance.getDouble(0) && extensionEncoder.getPosition() >= setpoint - tolerance.getDouble(0)) {
         
            return true;
        } else {

            return false;
        }
    }

    private void calcEnums() {

        if (ExtendLength.EXTENDFULL.equals(currentExtendLength)) {

            setpoint = highExtend;
        } else if (ExtendLength.RETRACTFULL.equals(currentExtendLength)) {

            setpoint = minValue;
        } else if (ExtendLength.ZERO.equals(currentExtendLength)) {

            setpoint = 0.0;
        }
    }

    @Override
    public void periodic() {

        extensionMotorSetpoint.setDouble(setpoint);
        extensionPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }
    
}
