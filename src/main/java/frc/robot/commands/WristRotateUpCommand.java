package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;

public class WristRotateUpCommand extends CommandBase {
    
    WristSubsystem wristSubsystem;

    public WristRotateUpCommand(WristSubsystem wristSubsystem) {

        this.wristSubsystem = wristSubsystem;
    }
    
    @Override
    public void execute() {

        wristSubsystem.wristUp();
    }

    @Override
    public void end(boolean interrupted) {

        
    }


    
}
