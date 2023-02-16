package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;

public class ArmExtensionCommand extends CommandBase {

    private ArmExtensionSubsystem armExtensionSubsystem;
    private double setPoint;
    ArmRotationSubsystem armRotationSubsystem;

    public ArmExtensionCommand(ArmExtensionSubsystem armExtensionSubsystem, double setPoint, ArmRotationSubsystem armRotationSubsystem) {

        this.armExtensionSubsystem = armExtensionSubsystem;
        this.setPoint = setPoint;
        addRequirements(armExtensionSubsystem);
        this.armRotationSubsystem = armRotationSubsystem;
    }

    @Override
    public void execute() {
        
        armExtensionSubsystem.setExtensionLength(setPoint);
        armRotationSubsystem.stepNum.setDouble(3);
    }

    @Override 
    public boolean isFinished() {
       return true;
    }
}