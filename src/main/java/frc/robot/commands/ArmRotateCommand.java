package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;


public class ArmRotateCommand extends CommandBase {
    private ArmRotationSubsystem armRotationSubsystem = null;

    public ArmRotateCommand(ArmRotationSubsystem armRotationSubsystem) {
        this.armRotationSubsystem = armRotationSubsystem;
        addRequirements(armRotationSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        armRotationSubsystem.armRotate();
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        armRotationSubsystem.stop();
    }

}