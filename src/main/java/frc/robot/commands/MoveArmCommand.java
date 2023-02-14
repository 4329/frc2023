package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public abstract class MoveArmCommand extends InstantCommand {

    private ArmRotationSubsystem armSubsystem;
    private ArmHeight armHeight;

    public MoveArmCommand(ArmRotationSubsystem armSubsystem, ArmHeight armHeight) {

        this.armSubsystem = armSubsystem;
        this.armHeight = armHeight;

        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {

        armSubsystem.setArmPosition(armHeight);
    }
}
