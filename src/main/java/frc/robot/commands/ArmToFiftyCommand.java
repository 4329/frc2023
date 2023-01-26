package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ArmToFiftyCommand extends CommandBase {

    private ArmSubsystem armSubsystem;

    public ArmToFiftyCommand(ArmSubsystem armSubsystem, double setPoint) {

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
