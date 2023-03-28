package frc.robot.subsystems;

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
        armMotor1.setInverted(true); //TODO INVESTIGATE SILLY ARM BOI
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

        unBrake();

        if (ArmHeight.HIGH.equals(currentArmHeight)) {

            setpoint = highPos;
        } else if (ArmHeight.MID.equals(currentArmHeight)) {

            setpoint = midPos;
        } else if (ArmHeight.LOW.equals(currentArmHeight)) {

            setpoint = lowPos;
        } else if (ArmHeight.SAFEEXTEND.equals(currentArmHeight)) {

            setpoint = safeExtendPos;
        } else if (ArmHeight.PORTAL.equals(currentArmHeight)) {

            setpoint = portalPos;
        } else if (ArmHeight.ZERO.equals(currentArmHeight)) {
            
            setpoint = zeroPos;
        } else if (ArmHeight.FLOOR.equals(currentArmHeight)) {

            setpoint = floorPos;
        }
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