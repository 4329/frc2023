package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ArmToPositionCommand extends CommandBase {

    private ArmSubsystem armSubsystem;
    private double setPoint;

    public ArmToPositionCommand(ArmSubsystem armSubsystem, double setPoint) {

        this.armSubsystem = armSubsystem;
        this.setPoint = setPoint;
        addRequirements(armSubsystem);
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
