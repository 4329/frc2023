package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class HighArmCommand extends MoveArmCommand {

    public HighArmCommand(ArmRotationSubsystem armRotationSubsystem) {
        
        super(armRotationSubsystem, ArmHeight.HIGH);
    }
   
}