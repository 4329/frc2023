package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ManualColorDetector;

public class ToggleElementCommand extends InstantCommand {

    ManualColorDetector manualColorDetector;

    public ToggleElementCommand(ManualColorDetector manualColorDetector) {

        this.manualColorDetector = manualColorDetector;
    }

    @Override
    public void initialize() {

        manualColorDetector.toggleElement();
    }

}