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
    private double setpoint;
    public GenericEntry armMotorSetpoint;
    public GenericEntry pidGraph;
    public final float maxValue;
    public final float minValue;

    public final double highPos;
    public final double midPos;
    public final double lowPos;
    public enum ArmHeight {
        HIGH,
        MID,
        LOW
    }

    public ArmHeight armheight;

    public ArmRotationSubsystem() {

        maxValue = 28f;
        minValue = 0f;

        highPos = 9;
        midPos = 7.75;
        lowPos = 0;

        armMotor1 = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armRotation1);
        armMotor2 = SparkFactory.createCANSparkMax(Constants.CANIDConstants.armRotation2);
        armMotor1.restoreFactoryDefaults();
        armMotor2.restoreFactoryDefaults();
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
        armPID.setD(0);
        armPID.setIZone(0);
        armPID.setFF(0);
        armPID.setOutputRange(-0.05, 1);
        armMotor1.burnFlash();
        armMotor2.burnFlash();
        setpoint = 0;

        pidGraph = Shuffleboard.getTab("setpoints").add("graph", 1).withWidget(BuiltInWidgets.kGraph).getEntry();
        armMotorSetpoint = Shuffleboard.getTab("setpoints").add("Arm Rotation Motor", 1).getEntry();
        
    }

    public void setArmPosition(double setpoint) {

        this.setpoint = setpoint;

        armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    private double armRotationSpeed = 0.03;

    public void armRotate() {

        if (setpoint < maxValue) {
            setpoint += armRotationSpeed;
            armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
        }
    }

    public void armUnrotate() {

        if (setpoint > minValue) {

            setpoint -= armRotationSpeed;
            armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
        }
    }
    
    private void calcEnums() {

        if (armheight == ArmHeight.HIGH) {

            setpoint = highPos;
        } else if (armheight == ArmHeight.MID) {
            
            setpoint = midPos;
        } else if (armheight == ArmHeight.LOW) {

            setpoint = lowPos;
        }
    }

    public void setArmPosition(ArmHeight armheight) {

        this.armheight = armheight;
        calcEnums();
    }

    public void stop() {
        armMotor1.set(0);
    }

    public boolean armAtSetpoint() {

        return false;
    }

    public void resetSetpoint() {

        setpoint = 0;
        armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    @Override
    public void periodic() {


        armMotorSetpoint.setDouble(setpoint);
        pidGraph.setDouble(armEncoder.getPosition());
    }

    
}
