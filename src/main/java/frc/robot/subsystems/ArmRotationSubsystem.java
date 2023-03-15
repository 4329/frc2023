package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utilities.SparkFactory;

public class ArmRotationSubsystem extends SubsystemBase {

    private CANSparkMax armMotor1;
    private CANSparkMax armMotor2;
    private DoubleSolenoid brakeSolenoid;
    
    private RelativeEncoder armEncoder;
    private SparkMaxPIDController armPID;

    private double setpoint;
    private boolean braking;

    public GenericEntry armMotorSetpoint;
    public GenericEntry pidGraph;
    public GenericEntry brakingEntry;
    public GenericEntry ArmHeightEnum;
    
    private final double tolerance;

    private final float maxValue;
    private final float minValue;
    private final double highPos;
    private final double midPos;
    private final double lowPos;
    private final double safeExtendPos;
    private final double portalPos;
    private final double zeroPos;
    private final double floorPos;

    private final Map<ArmHeight, Double> armHeights;
    
    
    public ArmHeight currentArmHeight;

    public enum ArmHeight {

        HIGH,
        MID,
        LOW,
        SAFEEXTEND,
        PORTAL,
        FLOOR,
        ZERO
    }

    public ArmRotationSubsystem() {

        maxValue = 63f;
        minValue = 0f;

        tolerance = 0.2;

        highPos = 41;
        midPos = 38.75;
        lowPos = 13.5;
        safeExtendPos = 17.5;
        portalPos = 37.75;
        floorPos = 13.5; //was 10.25
        zeroPos = 0;

        // armMotor1.restoreFactoryDefaults();
        // armMotor2.restoreFactoryDefaults();
        armHeights = new HashMap<>();
        armHeights.put(ArmHeight.HIGH, highPos);
        armHeights.put(ArmHeight.MID, midPos);
        armHeights.put(ArmHeight.LOW, lowPos);
        armHeights.put(ArmHeight.SAFEEXTEND, safeExtendPos);
        armHeights.put(ArmHeight.PORTAL, portalPos);
        armHeights.put(ArmHeight.FLOOR, floorPos);
        armHeights.put(ArmHeight.ZERO, zeroPos);
        

        armMotor1 = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armRotation1, true);
        armMotor2 = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armRotation2, true);
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
        ArmHeightEnum = Shuffleboard.getTab("setpoints").add("where", "Zero").getEntry();
        brakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);    

        // TODO slow drive speed when arm is extended. Keep arm at minimum possible extension.

        pidGraph = Shuffleboard.getTab("setpoints").add("graph", 1).withWidget(BuiltInWidgets.kGraph).getEntry();
        armMotorSetpoint = Shuffleboard.getTab("setpoints").add("Arm Rotation Motor", 1).getEntry();
        brakingEntry = Shuffleboard.getTab("setpoints").add("Braking", false).getEntry();
    }

    public void setArmPosition(ArmHeight armHeight) {

        this.currentArmHeight = armHeight;
        calcEnums();
        ArmHeightEnum.setString(armHeight.toString());
    }

    public ArmHeight getArmPosition() {

        return this.currentArmHeight;
    }

    public void armRotate() {

        if (setpoint < maxValue) {
            setpoint += Constants.ArmRotationConstants.armRotateSpeed;
            unBrake();
        }
    }

    public void armUnrotate() {

        if (setpoint > minValue) {

            setpoint -= Constants.ArmRotationConstants.armRotateSpeed;
            unBrake();
        }
    }

    private void calcEnums() {
        
        setpoint = armHeights.get(currentArmHeight);
    }

    public void resetSetpoint() {

        setpoint = armEncoder.getPosition();
    }

    public void stop() {

        armMotor1.set(0);
        brake();
    }

    public boolean armAtSetpoint() {

        if (armEncoder.getPosition() <= setpoint + tolerance && armEncoder.getPosition() >= setpoint - tolerance) {

            brake();
            return true;
        } else {

            return false;
        }
    }

    public boolean isLowerThanSafeExtend() {

        if (armEncoder.getPosition() < safeExtendPos) {

            return true;
        } else {

            return false;
        }
    }

    private void brake() {

        brakeSolenoid.set(DoubleSolenoid.Value.kForward);
        braking = true;
        brakingEntry.setBoolean(braking);
   }

    private void unBrake() {

        brakeSolenoid.set(DoubleSolenoid.Value.kReverse);
        braking = false;
        brakingEntry.setBoolean(braking);
   }

   @Override
    public void periodic() {

        armMotorSetpoint.setDouble(setpoint);
        pidGraph.setDouble(armMotor1.getAppliedOutput());
        armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

}