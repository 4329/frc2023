package frc.robot.commands.extend;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;

public class ArmRetractFullCommand extends ArmExtensionBase {

    public ArmRetractFullCommand(ArmExtensionSubsystem armExtensionSubsystem) {

        super(armExtensionSubsystem, ExtendLength.RETRACTFULL);

    }
}