package frc.robot.commands.extend;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmExtensionSubsystem.ExtendLength;

public abstract class ArmExtensionBase extends CommandBase {

    private ArmExtensionSubsystem armExtensionSubsystem;
    private ExtendLength setPoint;

    public ArmExtensionBase(ArmExtensionSubsystem armExtensionSubsystem, ExtendLength setPoint) {

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