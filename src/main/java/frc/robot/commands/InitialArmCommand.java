package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class InitialArmCommand extends MoveArmCommand {

    ArmRotationSubsystem armRotationSubsystem;

    public InitialArmCommand(ArmRotationSubsystem armRotationSubsystem) {

        super(armRotationSubsystem, ArmHeight.INITIAL);
        this.armRotationSubsystem = armRotationSubsystem;
    }

}