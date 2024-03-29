package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetectorSubsystem;

public class IntakeCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private ColorDetectorSubsystem colorDetector;

    public IntakeCommand(ClawSubsystem clawSubsystem, ColorDetectorSubsystem colorDetector) {

        this.colorDetector = colorDetector;
        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        clawSubsystem.intake();

    }

    @Override
   public boolean isFinished() {

        // if (colorDetector.distance() > 500) {

        //     return true;
        // } else {

        //     return false;
        // }
            return false;
            
    }

    @Override
    public void end(boolean interrupted) {

        clawSubsystem.stop();

    }

}