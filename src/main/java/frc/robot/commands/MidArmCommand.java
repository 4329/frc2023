package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class MidArmCommand extends MoveArmCommand {

    public MidArmCommand(ArmRotationSubsystem armSubsystem) {
        
        super(armSubsystem, ArmHeight.MID);
    }

}