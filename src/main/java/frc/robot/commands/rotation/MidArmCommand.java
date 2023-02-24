package frc.robot.commands.rotation;

import frc.robot.commands.rotation.MoveArmCommand;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class MidArmCommand extends MoveArmCommand {

    public MidArmCommand(ArmRotationSubsystem armSubsystem) {
        
        super(armSubsystem, ArmHeight.MID);
    }

}