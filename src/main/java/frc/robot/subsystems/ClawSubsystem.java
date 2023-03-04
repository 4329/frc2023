package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.ColorDetector.FieldElement;
import frc.robot.utilities.SparkFactory;

public class ClawSubsystem extends SubsystemBase {

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    private DoubleSolenoid solenoid;
    private ColorDetector colorDetector;
    private GenericEntry clawOpen;
    private GenericEntry fdjsial;
    public boolean clawing;
    private boolean intaking = false;
    public double speed;
    private GenericEntry intakafying;

    public ClawSubsystem(ColorDetector colorDetector) {

        clawOpen = Shuffleboard.getTab("RobotData").add("Claw closed", true).getEntry();
        intakafying = Shuffleboard.getTab("RobotData").add("intaking",false).getEntry();
        leftMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.clawLeft);
        rightMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.clawRight);
        solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 0);
        this.colorDetector = colorDetector;
        leftMotor.setInverted(true);
        leftMotor.setIdleMode(IdleMode.kBrake);
        rightMotor.setIdleMode(IdleMode.kBrake);
        fdjsial = Shuffleboard.getTab("setpoints").add("whynot", 0.285).getEntry();

    }

    public void intake() {

        speed = -0.2;
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void outtakeHigh(FieldElement fieldElement) {

        speed = 0.40;
        if (FieldElement.CUBE.equals(fieldElement)) {

        speed = 0.2;
        }
        // fdjsial.setDouble(speed);
        leftMotor.set(speed);
        rightMotor.set(speed);

    }

    public void outtakeMid(FieldElement fieldElement) {

        speed = fdjsial.getDouble(0);
        if (FieldElement.CUBE.equals(fieldElement)) {

        speed = 0.05;
        }
        // fdjsial.setDouble(speed);
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void outtakeLow(FieldElement fieldElement) {

        speed = fdjsial.getDouble(0);
        if (FieldElement.CUBE.equals(fieldElement)) {

        speed = 0.15;
        }
        // fdjsial.setDouble(speed);
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void stop() {

        double stop = 0;
        leftMotor.set(stop);
        rightMotor.set(stop);
    }

    public void togglePinch() {

        if (clawing) {

            solenoid.set(Value.kReverse);
        } else {

            solenoid.set(Value.kForward);
        } 
        clawing = !clawing;
        clawOpen.setBoolean(clawing);
    }

    public void pinch() {

        solenoid.set(Value.kForward);
        clawOpen.setBoolean(true);
    }

    public void release() {

        solenoid.set(Value.kReverse);
        clawOpen.setBoolean(false);
    }

    public boolean outtakeSuccessful() {

        if (colorDetector.distance() < 200) {

            return true;
        } else {

            return false;
        }
    }

    public void toggleIntake() {

        if (intaking) {

            stop();
        } else {

            intake();
        }

        intaking = !intaking;
        intakafying.setBoolean(intaking);
    }

}