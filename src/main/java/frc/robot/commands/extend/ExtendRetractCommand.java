package frc.robot.commands.extend;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
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
            armExtensionSubsystem.extend();
        } else if (retraction > 0.3) {
            armExtensionSubsystem.retract();
        }
    }    

}