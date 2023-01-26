package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class MoveArmCommand extends CommandBase {

    private ArmSubsystem armSubsystem;

    public MoveArmCommand(ArmSubsystem armSubsystem, double setPoint) {

        this.armSubsystem = armSubsystem;

        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {

        armSubsystem.setArmPosition(50);

    }

    @Override
    public boolean isFinished() {

        return armSubsystem.armAtSetpoint();
        
    }
}
