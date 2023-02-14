package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;

public class MidArmCommand extends MoveArmCommand {

    public MidArmCommand(ArmRotationSubsystem armSubsystem) {
        
        super(armSubsystem, armSubsystem.midPos);
    }

}