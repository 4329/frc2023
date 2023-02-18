package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public class WristZeroCommand extends WristToPositionCommand {

   
    public WristZeroCommand(WristSubsystem wristSubsystem) {

        super(wristSubsystem, WristAngle.ZERO);
        
    }

   
    
}
