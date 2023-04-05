package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetectorSubsystem;

public class ManualHighShotCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private CommandXboxController controller;
    private ColorDetectorSubsystem colorDetector;

    public ManualHighShotCommand(ClawSubsystem clawSubsystem, CommandXboxController commandXboxController, ColorDetectorSubsystem colorDetector) {

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

        double extension = controller.getRightTriggerAxis();
        if (extension > 0.3) {

            clawSubsystem.outtakeHigh(colorDetector.getCurrentElement());
        } else {

            clawSubsystem.stop();
        }
    }

    @Override
    public void end(boolean interrupted) {

        clawSubsystem.stop();
        System.out.println("fsudafhkjsdjlkfjlsadlkfjlksadjlffjoisadodijfosalijfioweajiojfoisajorioasjdoi;jfoi;osajl;fjlksadjlkfjsalkf");
    }



}