package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;

public class HighArmCommand extends CommandBase {

    private ArmRotationSubsystem armSubsystem;

    public HighArmCommand(ArmRotationSubsystem armSubsystem) {

        this.armSubsystem = armSubsystem;
        
        addRequirements(armSubsystem);
        // armSetpoint = Shuffleboard.getTab("setpoints").add("arm setpoint", 7.75).getEntry();
    }

    @Override
    public void initialize() {

        armSubsystem.highArmPosition();

    }

    @Override
    public boolean isFinished() {

        return armSubsystem.armAtSetpoint();
        
    }
}
