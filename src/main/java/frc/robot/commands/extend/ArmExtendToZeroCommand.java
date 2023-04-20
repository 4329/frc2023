package frc.robot.commands.extend;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;
import frc.robot.subsystems.ColorDetectorSubsystem;
import frc.robot.subsystems.ColorDetectorSubsystem.FieldElement;

public class ArmExtendToZeroCommand extends CommandBase {
    private ArmExtensionSubsystem armExtensionSubsystem;
    private ExtendLength setPoint;
    private ColorDetectorSubsystem colorDetectorSubsystem;

    public ArmExtendToZeroCommand(ArmExtensionSubsystem armExtensionSubsystem,
            ColorDetectorSubsystem colorDetectorSubsystem) {
        this.armExtensionSubsystem = armExtensionSubsystem;
        this.setPoint = setPoint;
        this.colorDetectorSubsystem = colorDetectorSubsystem;
        addRequirements(armExtensionSubsystem);

    }

    @Override
    public void execute() {

        if (FieldElement.CUBE.equals(colorDetectorSubsystem.getCurrentElement())) {

            setPoint = ExtendLength.CUBESTOW;

        } else {

            setPoint = ExtendLength.ZERO;
        }

        armExtensionSubsystem.setExtensionLength(setPoint);

    }



    @Override
    public boolean isFinished() {

        return armExtensionSubsystem.extendAtSetpoint();
    }
}