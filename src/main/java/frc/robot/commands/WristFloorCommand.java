package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public class WristFloorCommand extends WristToPositionCommand {

   
    public WristFloorCommand(WristSubsystem wristSubsystem) {

        super(wristSubsystem, WristAngle.FLOOR);
        
    }

   
    
}