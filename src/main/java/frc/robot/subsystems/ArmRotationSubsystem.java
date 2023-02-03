package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
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
    private GenericEntry khaikun;
    private GenericEntry khaichan;

    public ArmRotationSubsystem() {

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
        armMotor1.setSoftLimit(SoftLimitDirection.kForward, 10);
        armMotor1.setSoftLimit(SoftLimitDirection.kReverse, -1);
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

    public void setArmPosition(Double setpoint) {

        double errorBound = (armPID.getOutputMax() - armPID.getOutputMin()) / 2.0;

        double setpointerror1 = MathUtil.inputModulus(setpoint - armEncoder.getPosition(), -errorBound, errorBound);
        double setpointerror2 = MathUtil.inputModulus(setpoint - armMotor2.getEncoder().getPosition(), -errorBound, errorBound);

        // if (m_continuous) {
        // double errorBound = (m_maximumInput - m_minimumInput) / 2.0;
        // m_positionError = MathUtil.inputModulus(m_setpoint - m_measurement,
        // -errorBound, errorBound);
        // } else {
        // m_positionError = m_setpoint - m_measurement;
        // }

        armPID.setReference(setpoint, CANSparkMax.ControlType.kPosition);
        khaikun.setDouble(setpointerror1);
        khaichan.setDouble(setpointerror2);
    }

    private double armRotationSpeed = 0.1;

    public void armRotate() {
        armMotor1.set(armRotationSpeed);
        //armMotor2.set(armRotationSpeed);
    }
    public void armUnrotate(){
        armMotor1.set(-armRotationSpeed);
        //armMotor2.set(-armRotationSpeed);
    }
    public void stop(){
        armMotor1.set(0);
        //armMotor2.set(0);
    }

    public boolean armAtSetpoint() {

        return false;
    }

}