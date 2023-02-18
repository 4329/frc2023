package frc.robot.commands;

import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public class MidWristCommand extends WristToPositionCommand {

    public MidWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, WristAngle.MIDROT);
    }
    
}
