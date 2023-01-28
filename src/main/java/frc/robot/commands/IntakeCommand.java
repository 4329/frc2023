package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class IntakeCommand extends CommandBase {
    private ClawSubsystem clawSubsystem = null;

    public IntakeCommand(ClawSubsystem clawSubsystem) {
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

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        clawSubsystem.stop();
    }

}
