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

    public AprilTagMiddleCommand(LimlighSubsystem limlighSubsystem, double targetId, Drivetrain m_drivetrain) {

        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        centerPID = new PIDController(-0.001, 0, 0);
        centerPID.setTolerance(0.5);
        forwardPID = new PIDController(-0.001, 0, 0);
        this.targetId = targetId;
        addRequirements(limlighSubsystem, m_drivetrain);
    }

    @Override
    public void initialize() {
        

    }

    @Override
    public void execute() {
        
        if (this.limlighSubsystem.getTargetId() == targetId) {
            
            centerPID.setSetpoint(0);
            forwardPID.setSetpoint(150);
            drivetrain.drive(forwardPID.calculate(limlighSubsystem.getTargetz()), centerPID.calculate(limlighSubsystem.getTargetx()), 0, false);
            System.out.println("I am movin WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWw");

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
