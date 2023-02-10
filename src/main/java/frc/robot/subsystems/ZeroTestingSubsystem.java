package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.SparkFactory;

public class ZeroTestingSubsystem extends SubsystemBase {

    private CANSparkMax extensionMotor;
    private GenericEntry dsajl;
    private GenericEntry dsajl2;

    public ZeroTestingSubsystem() {

        extensionMotor = SparkFactory.createCANSparkMax(42);
        extensionMotor.setSmartCurrentLimit(1);
        extensionMotor.burnFlash();
        dsajl = Shuffleboard.getTab("djs").add("kop;k", 1).withWidget(BuiltInWidgets.kGraph).getEntry();
        dsajl2 = Shuffleboard.getTab("djs").add("text", false).getEntry();
    }

     4.23

    public void BopItSpinIt() {

        extensionMotor.set(0.1);
    }

    public void YouLose() {

        extensionMotor.set(0);
    }

    @Override
    public void periodic() {

        dsajl.setDouble(extensionMotor.getOutputCurrent());
        if (extensionMotor.getOutputCurrent() > 0) {
            dsajl2.setBoolean(true);
        } else {
            dsajl2.setBoolean(false);

        }
    }

}