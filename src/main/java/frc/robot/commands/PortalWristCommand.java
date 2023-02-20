package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public class PortalWristCommand extends WristToPositionCommand {

    public PortalWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, WristAngle.PORTALROT);
    }
    
}
