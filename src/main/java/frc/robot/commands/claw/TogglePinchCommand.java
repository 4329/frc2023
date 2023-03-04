package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClawSubsystem;

public class TogglePinchCommand extends InstantCommand {

    private ClawSubsystem clawSubsystem;

    public TogglePinchCommand(ClawSubsystem clawSubsystem) {

        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    }

    @Override
    public void initialize() {

        clawSubsystem.togglePinch();
    }

}
