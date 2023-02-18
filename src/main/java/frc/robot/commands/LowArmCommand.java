package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class LowArmCommand extends MoveArmCommand {

    public LowArmCommand(ArmRotationSubsystem armRotationSubsystem) {

        super(armRotationSubsystem, ArmHeight.LOW);
    }
    
}