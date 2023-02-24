package frc.robot.commands.wrist;

import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public class HighWristCommand extends WristToPositionCommand {

    public HighWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, WristAngle.HIGHROT);
    }
    
}
