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

    public ClawSubsystem(ColorDetector colorDetector) {

        clawOpen = Shuffleboard.getTab("setpoints").add("Claw is Forward", true).getEntry();

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

        double speed = -0.2;
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void outtakeHigh() {

        double speed = 0.285;
        if (FieldElement.CUBE.equals(colorDetector.detectElement())) {

            speed = 0.20;
        }
        fdjsial.setDouble(speed);
        leftMotor.set(speed);
        rightMotor.set(speed);

    }

    public void outtakeMid() {

        double speed = 0.285;
        if (FieldElement.CUBE.equals(colorDetector.detectElement())) {

            speed = 0.05;
        }
        fdjsial.setDouble(speed);
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void outtakeLow() {

        double speed = 0.285;
        if (FieldElement.CUBE.equals(colorDetector.detectElement())) {

            speed = 0.15;
        }
        fdjsial.setDouble(speed);
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void stop() {

        double stop = 0;
        leftMotor.set(stop);
        rightMotor.set(stop);
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

}