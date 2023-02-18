package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;

public class PortalWristCommand extends WristToPositionCommand {

    public PortalWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, -5);
    }
    
}
