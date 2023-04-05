package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

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

public class ArmExtensionSubsystem extends SubsystemBase {

    private CANSparkMax extensionMotor;
    private RelativeEncoder extensionEncoder;
    private SparkMaxPIDController extensionPID;
    private double setpoint;
    private GenericEntry extensionMotorSetpoint;
    private GenericEntry tolerance;
    GenericEntry yihfsd;

    private final double highExtend;
    private final double midExtend;
    private final double floorExtend;
    private final double startExtend;
    private final double fullRetractLength;
    private final double fullExtendLength;
    private final double midScoreExtend;

    private final float maxValue;
    private final float minValue;

    
    public enum ExtendLength {
        
        RETRACTFULL,
        EXTENDFULL,
        FLOOR,
        ZERO,
        MID,
        HIGH,
        START,
        MIDSCORE
    }
    private final Map<ExtendLength, Double> kgndsln;

    private ExtendLength currentExtendLength;

    public ArmExtensionSubsystem() {

        maxValue = 220f;
        minValue = -16f; //it's a float - Matthew

        fullRetractLength = minValue;
        fullExtendLength = maxValue;
        midExtend = 120;
        highExtend = -3;
        floorExtend = 186.5;
        startExtend = -4;
        midScoreExtend = 113;

        kgndsln = new HashMap<>();
        kgndsln.put(ExtendLength.RETRACTFULL, fullRetractLength);
        kgndsln.put(ExtendLength.EXTENDFULL, fullExtendLength);
        kgndsln.put(ExtendLength.MID, midExtend);
        kgndsln.put(ExtendLength.FLOOR, floorExtend);
        kgndsln.put(ExtendLength.ZERO, 0.0);
        kgndsln.put(ExtendLength.HIGH, highExtend);
        kgndsln.put(ExtendLength.START, startExtend);
        kgndsln.put(ExtendLength.MIDSCORE, midScoreExtend);

        yihfsd = Shuffleboard.getTab("setpoints").add("haahhaha", 0).getEntry();


        extensionMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armExtension, false);
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
        extensionPID.setP(1);
        extensionPID.setI(0);
        extensionPID.setD(1);
        extensionPID.setIZone(0);
        extensionPID.setFF(0);
        extensionPID.setOutputRange(-1, 1);
        extensionMotor.burnFlash();
 
        tolerance = Shuffleboard.getTab("setpoints").add("armex tolerance", 2).getEntry();
        extensionMotorSetpoint = Shuffleboard.getTab("setpoints").add("Arm Extension Motor", false).getEntry();
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
         
            extensionMotorSetpoint.setBoolean(true);
            return true;
        } else {

            extensionMotorSetpoint.setBoolean(false);

            return false;
        }
    }

    private void calcEnums() {
    
        setpoint = kgndsln.get(currentExtendLength);
        
    }

    @Override
    public void periodic() {

        yihfsd.setDouble(extensionEncoder.getPosition() - setpoint);
        extensionPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }
    
}
