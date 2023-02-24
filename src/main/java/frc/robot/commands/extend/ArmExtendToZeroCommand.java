package frc.robot.commands.extend;

import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;

public class ArmExtendToZeroCommand extends ArmExtensionBase {

    public ArmExtendToZeroCommand(ArmExtensionSubsystem armExtensionSubsystem) {

        super(armExtensionSubsystem, ExtendLength.ZERO);
    }
    
}