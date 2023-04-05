package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetectorSubsystem;
import frc.robot.subsystems.ColorDetectorSubsystem.FieldElement;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ConeZeroCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private ColorDetectorSubsystem colorDetector;

    private boolean finished;

    private double waitTime;

    public ConeZeroCommand(ClawSubsystem clawSubsystem, ColorDetectorSubsystem colorDetector) {

        this.colorDetector = colorDetector;
        this.clawSubsystem = clawSubsystem;
        addRequirements(clawSubsystem, colorDetector);
    }

    @Override
    public void initialize() {

        waitTime = 0;

        if (FieldElement.CONE.equals(colorDetector.getCurrentElement()) && clawSubsystem.clawing) {

            finished = false;
        } else {

            finished = true;
        }
    }

    @Override
    public void execute() {

        if (!finished) {
            
            waitTime++;

            if (waitTime > 1 && waitTime < 10) {

                clawSubsystem.release();
            } else if (waitTime < 20) {

                clawSubsystem.pinch();
            } else if (waitTime < 60) {

                clawSubsystem.intake();
            } else {

                finished = true;
            }
        }
    }

    @Override
    public void end(boolean interrupt) {

        clawSubsystem.stop();
    }

    @Override
    public boolean isFinished() {

        return finished;
    }

}
