package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class ExtendRetractCommand extends CommandBase {

    private ArmExtensionSubsystem armExtensionSubsystem;
    private CommandXboxController controller;

    public ExtendRetractCommand(ArmExtensionSubsystem armExtensionSubsystem, CommandXboxController commandXboxController) {

        this.armExtensionSubsystem = armExtensionSubsystem;
        this.controller = commandXboxController;
        addRequirements(armExtensionSubsystem);

    }

    @Override
    public void execute() {
        double extension = controller.getRightTriggerAxis();
        double retraction = controller.getLeftTriggerAxis();
        if (extension > 0.3) {
            armExtensionSubsystem.extend(extension * Constants.ArmConstants.armSpeed);
        } else if (retraction > 0.3) {
            armExtensionSubsystem.retract(retraction * Constants.ArmConstants.armSpeed);
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

}