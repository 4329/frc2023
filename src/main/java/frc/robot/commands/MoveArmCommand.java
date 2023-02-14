package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ArmRotationSubsystem;

public abstract class MoveArmCommand extends InstantCommand {

    private ArmRotationSubsystem armSubsystem;
    private double setpoint;

    public MoveArmCommand(ArmRotationSubsystem armSubsystem, double setpoint) {

        this.armSubsystem = armSubsystem;
        this.setpoint = setpoint;

        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {

        armSubsystem.setArmPosition(setpoint);
    }
}
