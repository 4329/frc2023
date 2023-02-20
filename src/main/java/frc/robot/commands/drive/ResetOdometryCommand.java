package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.swerve.Drivetrain;

public class ResetOdometryCommand extends CommandBase{

    Pose2d pose;
    Drivetrain drivetrain;

    public ResetOdometryCommand(Pose2d pose, Drivetrain drivetrain) {

        this.pose = pose;
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {

        drivetrain.resetOdometry(pose);
    }

    @Override
    public boolean isFinished() {

        return true;
    }
    
}
