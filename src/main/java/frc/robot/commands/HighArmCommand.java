package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;

public class HighArmCommand extends MoveArmCommand {

    public HighArmCommand(ArmRotationSubsystem armSubsystem) {
        
        super(armSubsystem, armSubsystem.highPos);
    }

}