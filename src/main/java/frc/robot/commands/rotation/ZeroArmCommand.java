package frc.robot.commands.rotation;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class ZeroArmCommand extends MoveArmCommand {

    public ZeroArmCommand(ArmRotationSubsystem armRotationSubsystem) {
        
        super(armRotationSubsystem, ArmHeight.ZERO);
    }
   
}