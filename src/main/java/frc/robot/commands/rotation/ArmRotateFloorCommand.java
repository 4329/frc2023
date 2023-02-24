package frc.robot.commands.rotation;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class ArmRotateFloorCommand extends MoveArmCommand {

    public ArmRotateFloorCommand(ArmRotationSubsystem armRotationSubsystem) {

        super(armRotationSubsystem, ArmHeight.FLOOR);

    }

}