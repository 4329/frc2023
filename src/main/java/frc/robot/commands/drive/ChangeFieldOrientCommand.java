package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChangeFieldOrientCommand extends CommandBase{

    DriveByController driveByController;

    public ChangeFieldOrientCommand(DriveByController driveByController) {

        this.driveByController = driveByController;
    }

    @Override
    public void initialize() {

        driveByController.changeFieldOrient();
    }

    @Override
    public boolean isFinished() {

        return true;
    }

}
