package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class ReleaseCommand extends CommandBase {
    private ClawSubsystem clawSubsystem = null;

    public ReleaseCommand(ClawSubsystem clawSubsystem) {
        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    }

    @Override
    public void execute() {

        clawSubsystem.release();
    }

    @Override
    public boolean isFinished() {

        return true;
    }
}
