package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public abstract class ArmExtensionBase extends CommandBase {

    private ArmExtensionSubsystem armExtensionSubsystem;
    private double setPoint;

    public ArmExtensionBase(ArmExtensionSubsystem armExtensionSubsystem, double setPoint) {

        this.armExtensionSubsystem = armExtensionSubsystem;
        this.setPoint = setPoint;
        addRequirements(armExtensionSubsystem);
    }

    @Override
    public void execute() {
        
        armExtensionSubsystem.setExtensionLength(setPoint);
    }

    @Override 
    public boolean isFinished() {

       return armExtensionSubsystem.extendAtSetpoint();
    }
}