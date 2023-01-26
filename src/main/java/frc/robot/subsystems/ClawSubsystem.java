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

public class ClawSubsystem extends SubsystemBase {


    private GenericEntry fjaflijefli;
    private CANSparkMax armMotor;
    private CANSparkMax armMotoryes;


    public ClawSubsystem() {

        armMotor = new CANSparkMax(14, MotorType.kBrushless);
        armMotoryes = new CANSparkMax(13, MotorType.kBrushless);                    
        fjaflijefli = Shuffleboard.getTab("efij").add("dijool", 1).withWidget(BuiltInWidgets.kNumberSlider).getEntry();                                                

    }

    @Override
    public void periodic() {

        armMotor.set(fjaflijefli.getDouble(0));
        armMotoryes.set(fjaflijefli.getDouble(0));
    }

}