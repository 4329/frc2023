package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;

public class LowWristCommand extends WristToPositionCommand {

    public LowWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, 10);
    }
    
}
