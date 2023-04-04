package frc.robot.commands.rotation;

import frc.robot.commands.rotation.MoveArmCommand;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class ArmRotateMidScoreCommand extends MoveArmCommand {

    public ArmRotateMidScoreCommand(ArmRotationSubsystem armSubsystem) {
        
        super(armSubsystem, ArmHeight.MIDSCORE);
    }

}