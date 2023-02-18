package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utilities.SparkFactory;

public class ArmRotationSubsystem extends SubsystemBase {

    private CANSparkMax armMotor1;
    private CANSparkMax armMotor2;
    private RelativeEncoder armEncoder;
    private SparkMaxPIDController armPID;

    public double setpoint;
    public GenericEntry armMotorSetpoint;
    public GenericEntry pidGraph;

    
    private final float maxValue;
    private final float minValue;

    private final double tolerance;

    private final double highPos;
    private final double midPos;
    private final double lowPos;
    private final double initialPos;
    private final double portalPos;

    public ArmHeight currentArmHeight;

    public enum ArmHeight {
        HIGH,
        MID,
        LOW,
        INITIAL,
        PORTAL
    }

    public ArmRotationSubsystem() {

        maxValue = 43f;
        minValue = 0f;

        tolerance = 0.2;

        highPos = 37.75; //high is wrong
        midPos = 37.5;
        lowPos = 0;
        initialPos = 10;
        portalPos = 39;

        armMotor1 = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armRotation1);
        armMotor2 = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armRotation2);
        armMotor1.restoreFactoryDefaults();
        armMotor2.restoreFactoryDefaults();
        armMotor1.setInverted(true);
        armMotor1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        armMotor2.setIdleMode(CANSparkMax.IdleMode.kBrake);
        armMotor2.follow(armMotor1);
        armPID = armMotor1.getPIDController();
        armEncoder = armMotor1.getEncoder();
        armMotor1.enableSoftLimit(SoftLimitDirection.kForward, true);
        armMotor1.enableSoftLimit(SoftLimitDirection.kReverse, true);
        armMotor1.setSoftLimit(SoftLimitDirection.kForward, maxValue);
        armMotor1.setSoftLimit(SoftLimitDirection.kReverse, minValue);
        armEncoder.setPosition(0);
        armMotor1.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit);
        armMotor1.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
        armPID.setP(0.5);
        armPID.setI(0);
        armPID.setD(0.5);
        armPID.setIZone(0);
        armPID.setFF(0);
        armPID.setOutputRange(-0.3, 0.35);
        armMotor1.burnFlash();
        armMotor2.burnFlash();
        setpoint = 0;

        // TODO decrease rotate forward power. slow drive speed when arm is extended. Keep arm at minimum possible extension.

        pidGraph = Shuffleboard.getTab("setpoints").add("graph", 1).withWidget(BuiltInWidgets.kGraph).getEntry();
        armMotorSetpoint = Shuffleboard.getTab("setpoints").add("Arm Rotation Motor", 1).getEntry();
    }

    public void setArmPosition(ArmHeight armHeight) {

        this.currentArmHeight = armHeight;
        calcEnums();
    }

    public void armRotate() {

        if (setpoint < maxValue) {
            setpoint += Constants.ArmRotationConstants.armRotateSpeed;
        }
    }

    public void armUnrotate() {

        if (setpoint > minValue) {

            setpoint -= Constants.ArmRotationConstants.armRotateSpeed;
        }
    }

    private void calcEnums() {

        if (ArmHeight.HIGH.equals(currentArmHeight)) {

            setpoint = highPos;
        } else if (ArmHeight.MID.equals(currentArmHeight)) {

            setpoint = midPos;
        } else if (ArmHeight.LOW.equals(currentArmHeight)) {

            setpoint = lowPos;
        } else if (ArmHeight.INITIAL.equals(currentArmHeight)) {

            setpoint = initialPos;
        } else if (ArmHeight.PORTAL.equals(currentArmHeight)) {

            setpoint = portalPos;
        }
    }

    public void resetSetpoint() {

        setpoint = 0;
    }

    public void stop() {

        armMotor1.set(0);
    }

    public boolean armAtSetpoint() {

        if (armEncoder.getPosition() <= setpoint + tolerance && armEncoder.getPosition() >= setpoint - tolerance) {

            return true;
        } else {

            return false;
        }
    }

    @Override
    public void periodic() {

        armMotorSetpoint.setDouble(setpoint);
        pidGraph.setDouble(armEncoder.getPosition());
        armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

}
