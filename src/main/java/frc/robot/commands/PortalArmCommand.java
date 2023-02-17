package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class PortalArmCommand extends MoveArmCommand{

    public PortalArmCommand(ArmRotationSubsystem armRotationSubsystem) {

        super(armRotationSubsystem, ArmHeight.PORTAL);
    }
}