package frc.robot.commands.extend;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;

public class ArmExtendMidScoreCommand extends ArmExtensionBase {

    public ArmExtendMidScoreCommand(ArmExtensionSubsystem armExtensionSubsystem) {

        super(armExtensionSubsystem, ExtendLength.MIDSCORE);

    }
}