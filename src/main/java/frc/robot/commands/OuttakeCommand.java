package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class OuttakeCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;

    public OuttakeCommand(ClawSubsystem clawSubsystem) {

        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    }

    @Override
    public void execute() {

        clawSubsystem.outtake();
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
