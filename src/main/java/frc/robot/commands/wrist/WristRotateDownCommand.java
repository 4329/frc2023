package frc.robot.commands.wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;

public class WristRotateDownCommand extends CommandBase {
    
    WristSubsystem wristSubsystem;

    public WristRotateDownCommand(WristSubsystem wristSubsystem) {

        this.wristSubsystem = wristSubsystem;
    }
    
    @Override
    public void execute() {

        wristSubsystem.wristDown();
    }

    @Override
    public void end(boolean interrupted) {

        
    }


    
}
