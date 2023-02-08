package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class LowArmCommand extends CommandBase {

    private ArmRotationSubsystem armSubsystem;

    public LowArmCommand(ArmRotationSubsystem armSubsystem) {

        this.armSubsystem = armSubsystem;
        
        addRequirements(armSubsystem);
        // armSetpoint = Shuffleboard.getTab("setpoints").add("arm setpoint", 7.75).getEntry();
    }

    @Override
    public void initialize() {

        armSubsystem.setArmPosition(ArmHeight.LOW);

    }

    @Override
    public boolean isFinished() {

        return true;
        
    }
}