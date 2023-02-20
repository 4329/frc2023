package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class ArmRotateFloorCommand extends MoveArmCommand {

    public ArmRotateFloorCommand(ArmRotationSubsystem armRotationSubsystem) {

        super(armRotationSubsystem, ArmHeight.FLOOR);

    }

}