package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;

public class IntakeCompleteCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private ColorDetector colorDetector;

    public IntakeCompleteCommand(ClawSubsystem clawSubsystem, ColorDetector colorDetector) {

        this.clawSubsystem = clawSubsystem;
        this.colorDetector = colorDetector;
        addRequirements(clawSubsystem);

    }

    @Override
    public void initialize() {

        clawSubsystem.intake();

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {

        return colorDetector.holdingSomthing();

    }

    @Override
    public void end(boolean interrupted) {

        clawSubsystem.stop();
    
    }


}
