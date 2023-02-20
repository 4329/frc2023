package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class ZeroArmCommand extends MoveArmCommand {

    public ZeroArmCommand(ArmRotationSubsystem armRotationSubsystem) {
        
        super(armRotationSubsystem, ArmHeight.ZERO);
    }
   
}