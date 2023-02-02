package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utilities.SparkFactory;
import frc.robot.subsystems.ColorDetector.FieldElement;

public class ClawSubsystem extends SubsystemBase {

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    private DoubleSolenoid solenoid;
    private ColorDetector colorDetector;

    public ClawSubsystem() {

        leftMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.clawLeft);
        rightMotor = SparkFactory.createCANSparkMax(Constants.CANIDConstants.clawRight);
        solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
        colorDetector = new ColorDetector();
        leftMotor.setIdleMode(IdleMode.kBrake);
        rightMotor.setIdleMode(IdleMode.kBrake);
    }

    public void intake() {
        double speed = -0.2;
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void outtake() {
        double reverseSpeed;
        if (colorDetector.detectElement() == FieldElement.CUBE) {
            reverseSpeed = 0.2;
        } else {
            reverseSpeed = 0.6;
        }
        leftMotor.set(reverseSpeed);
        rightMotor.set(reverseSpeed);
    }

    public void stop() {

        double stop = 0;
        leftMotor.set(stop);
        rightMotor.set(stop);
    }

    public void pinch() {

        solenoid.set(Value.kForward);
    }

    public void release() {

        solenoid.set(Value.kReverse);
    }

}
