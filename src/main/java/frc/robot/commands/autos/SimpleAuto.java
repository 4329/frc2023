package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.AutoFromPathPlanner;

public class SimpleAuto extends SequentialCommandGroup{

    final AutoFromPathPlanner simpleAuto;

    public SimpleAuto(Drivetrain drive) {

    simpleAuto = new AutoFromPathPlanner(drive, "simpleAuto", Constants.AutoConstants.kMaxSpeed);

    addCommands(
        
        new InstantCommand(()->drive.resetOdometry(simpleAuto.getInitialPose())), simpleAuto
    );

    }
}
