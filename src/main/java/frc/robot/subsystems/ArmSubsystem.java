package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configrun;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ArmSubsystem extends SubsystemBase {

    private GenericEntry fjaflijefli;
    private CANSparkMax armMotor;
    private RelativeEncoder armEncoder;
    private PIDController armPID;

    public ArmSubsystem() {

        fjaflijefli = Shuffleboard.getTab("Ɛ>").add("wow so cool i love software", 0.4).getEntry();
        armMotor = new CANSparkMax(12, MotorType.kBrushless);
        armEncoder = armMotor.getEncoder();
        armEncoder.setPosition(0);
        armPID = new PIDController(fjaflijefli.getDouble(0), 0.3, 0);
        armPID.setTolerance(1);
        armMotor.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit); // Set current limit for the drive
                                                                                     // motor
        armMotor.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation); // Enable voltage compensation
                                                                                        // so

    }

    public void setArmPosition(int setPoint) {

        armPID.setSetpoint(setPoint);
    }

    @Override
    public void periodic() {

        double armPosition = armEncoder.getPosition();
        double output = armPID.calculate(armPosition, armPID.getSetpoint());
        armMotor.set(output);
    }

    public boolean armAtSetpoint() {

        return armPID.atSetpoint();
    }

}