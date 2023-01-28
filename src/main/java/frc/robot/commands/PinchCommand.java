package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class PinchCommand extends CommandBase {
    private ClawSubsystem clawSubsystem = null;

    public PinchCommand(ClawSubsystem clawSubsystem) {
        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    }
    @Override
    public void initialize() {
        clawSubsystem.release();
    }

    @Override
    public void execute() {
        clawSubsystem.pinch();
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }

}
