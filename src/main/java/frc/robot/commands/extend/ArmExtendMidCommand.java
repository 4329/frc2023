package frc.robot.commands.extend;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;

public class ArmExtendMidCommand extends ArmExtensionBase {

    public ArmExtendMidCommand(ArmExtensionSubsystem armExtensionSubsystem) {

        super(armExtensionSubsystem, ExtendLength.MID);

    }
}