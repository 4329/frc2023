package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;

public class ManualMidShotCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private CommandXboxController controller;
    private ColorDetector colorDetector;

    public ManualMidShotCommand(ClawSubsystem clawSubsystem, CommandXboxController commandXboxController, ColorDetector colorDetector) {

        this.controller = commandXboxController;
        this.colorDetector = colorDetector;
        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem);
    }

    @Override
    public void initialize() {
        
        clawSubsystem.stop();
    }

    @Override
    public void execute() {

        double extension = controller.getLeftTriggerAxis();
        if (extension > 0.3) {

            clawSubsystem.outtakeMid(colorDetector.getCurrentElement());
        } else {

            clawSubsystem.stop();
        }
    }

    @Override
    public void end(boolean interrupted) {

        clawSubsystem.stop();
    }

}