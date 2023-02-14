package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;

public class LowArmCommand extends MoveArmCommand {

    public LowArmCommand(ArmRotationSubsystem armRotationSubsystem) {

        super(armRotationSubsystem, armRotationSubsystem.lowPos);
    }
    
}