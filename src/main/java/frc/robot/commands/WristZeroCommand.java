package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;

public class WristZeroCommand extends WristToPositionCommand {

    public WristZeroCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, 0);
    }
    
}
