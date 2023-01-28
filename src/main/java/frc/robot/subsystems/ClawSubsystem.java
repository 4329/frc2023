package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    private DoubleSolenoid solenoid;

    public ClawSubsystem() {

        leftMotor = new CANSparkMax(14, MotorType.kBrushless);
        rightMotor = new CANSparkMax(13, MotorType.kBrushless);
        solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
    }

    @Override
    public void periodic() {

        /*
         * armMotor.set(fjaflijefli.getDouble(0));
         * armMotoryes.set(fjaflijefli.getDouble(0));
         */
    }

    public void intake() {
        double speed = -0.1;
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void outtake() {
        double reverseSpeed = 0.2;
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
