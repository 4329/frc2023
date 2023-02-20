package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;

public class AprilTagMiddleCommand extends CommandBase {

    LimlighSubsystem limlighSubsystem;

    PIDController centerPID;
    PIDController forwardPID;

    double targetId;

    Drivetrain drivetrain;

    public void aprilTagMiddleCommand(LimlighSubsystem limlighSubsystem, double targetId, Drivetrain drivetrain) {

        this.limlighSubsystem = limlighSubsystem;
        centerPID = new PIDController(0, 0, 0);
        centerPID.setTolerance(0.5);
        forwardPID = new PIDController(0, 0, 0);
        this.targetId = targetId;
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        

    }

    @Override
    public void execute() {
        
        if (limlighSubsystem.getTargetId() == targetId) {
            
            centerPID.setSetpoint(0);
            forwardPID.setSetpoint(150);
            drivetrain.drive(forwardPID.calculate(limlighSubsystem.getTargetz()), centerPID.calculate(limlighSubsystem.getTargetx()), 0, false);
        }
    }

    @Override
    public boolean isFinished() {

        return centerPID.atSetpoint() && forwardPID.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        
    }
    
}
