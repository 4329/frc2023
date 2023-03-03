package frc.robot.commands.claw;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;
import frc.robot.subsystems.ColorDetector.FieldElement;

public class ManualMidShotCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private CommandXboxController controller;
    private ColorDetector colorDetector;

    public ManualMidShotCommand(ClawSubsystem armExtensionSubsystem, CommandXboxController commandXboxController, ColorDetector colorDetector) {

        this.controller = commandXboxController;
        this.colorDetector = colorDetector;
        addRequirements(armExtensionSubsystem);
    }

    @Override
    public void execute() {

        double extension = controller.getLeftTriggerAxis();
        if (extension > 0.3) {

            clawSubsystem.outtakeMid(colorDetector.detectElement());
        }
    }

}