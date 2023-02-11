package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;

public class HighWristCommand extends WristToPositionCommand {

    public HighWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, -10);
    }
    
}
