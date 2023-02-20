package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.HoorayConfig;

public class BalanceCommand extends CommandBase{

    Drivetrain drivetrain;

    private double roll;

    public BalanceCommand(Drivetrain drivetrain) {

        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        roll = drivetrain.getRoll() / Constants.DriveConstants.maxRampRoll;

        if (Math.abs(roll) <= Constants.DriveConstants.maxRampDeviation || Math.abs(roll) >= Constants.DriveConstants.maxRampRoll + 5) {

            drivetrain.lock();
        } else {

            drivetrain.unlock();

            drivetrain.drive(HoorayConfig.gimmeConfig().getRollDirection() * roll * Constants.DriveConstants.maxRampSpeed, 0, 0, false);
        }
    }


    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.unlock();
    }


}