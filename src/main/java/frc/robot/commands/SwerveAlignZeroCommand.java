package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.swerve.Drivetrain;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveAlignZeroCommand extends CommandBase {

    Drivetrain drivetrain;

    public SwerveAlignZeroCommand(Drivetrain drivetrain) {

        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        drivetrain.setModuleStates(new SwerveModuleState[] {

            new SwerveModuleState(0, new Rotation2d()),
            new SwerveModuleState(0, new Rotation2d()),
            new SwerveModuleState(0, new Rotation2d()),
            new SwerveModuleState(0, new Rotation2d())
        });
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }

}
