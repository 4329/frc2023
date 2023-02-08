package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;

public class MoveArmCommand extends CommandBase {

    private ArmRotationSubsystem armSubsystem;
    private double setPoint;
    private GenericEntry armSetpoint;

    public MoveArmCommand(ArmRotationSubsystem armSubsystem, double setPoint) {

        this.armSubsystem = armSubsystem;
        this.setPoint = setPoint;
        
        addRequirements(armSubsystem);
        // armSetpoint = Shuffleboard.getTab("setpoints").add("arm setpoint", 7.75).getEntry();
    }

    @Override
    public void initialize() {

        armSubsystem.setArmPosition(setPoint);

    }

    @Override
    public boolean isFinished() {

        return armSubsystem.armAtSetpoint();
        
    }
}
