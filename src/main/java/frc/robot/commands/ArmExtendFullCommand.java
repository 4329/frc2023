package frc.robot.commands;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;

public class ArmExtendFullCommand extends ArmExtensionBase {

    public ArmExtendFullCommand(ArmExtensionSubsystem armExtensionSubsystem) {

        super(armExtensionSubsystem, ExtendLength.EXTENDFULL);

    }
}