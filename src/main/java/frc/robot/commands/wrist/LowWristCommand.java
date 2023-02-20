package frc.robot.commands.wrist;

import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public class LowWristCommand extends WristToPositionCommand {

    public LowWristCommand(WristSubsystem wristSubsystem) {
        super(wristSubsystem, WristAngle.LOWROT);
    }
    
}
