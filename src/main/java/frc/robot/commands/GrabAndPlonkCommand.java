package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;

public class GrabAndPlonkCommand extends CommandBase {

    private ColorDetector colorDetector;
    private ClawSubsystem clawSubsystem;
    private double startDistance;

    public GrabAndPlonkCommand(ColorDetector colorDetector, ClawSubsystem clawSubsystem) {

        this.colorDetector = colorDetector;
        this.clawSubsystem = clawSubsystem;
        
    }

    @Override
    public void initialize() {
        
        startDistance = colorDetector.distance();
    }

    @Override
    public void execute() {
        clawSubsystem.grabAndPlonk(startDistance);
    }
    
    @Override
    public boolean isFinished() {
        
        if (colorDetector.distance() > 500 && clawSubsystem.whaterver) {

            return true;
        } else {

            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
       
        clawSubsystem.stop();
    }

}