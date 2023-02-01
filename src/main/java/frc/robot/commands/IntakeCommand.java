package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;

import com.revrobotics.ColorSensorV3;

public class IntakeCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private ColorDetector colorDetector;

    public IntakeCommand(ClawSubsystem clawSubsystem, ColorDetector colorDetector) {

        this.colorDetector = colorDetector;
        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        if (colorDetector.distance() < 150) {

            clawSubsystem.intake();
        }
    }

    @Override
    public boolean isFinished() {

        if (colorDetector.distance() < 50) {

            return true;
        } else {

            return false;
        }

    }

    @Override
    public void end(boolean interrupted) {

        clawSubsystem.stop();
    }

}