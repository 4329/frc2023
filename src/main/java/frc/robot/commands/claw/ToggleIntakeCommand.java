package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ClawSubsystem;

public class ToggleIntakeCommand extends InstantCommand {

    private ClawSubsystem clawSubsystem;

    public ToggleIntakeCommand(ClawSubsystem clawSubsystem) {

        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    
    }

    @Override
    public void initialize() {
     
        clawSubsystem.toggleIntake();
    }


    


}
