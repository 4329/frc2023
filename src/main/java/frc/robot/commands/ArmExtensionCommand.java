package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtensionSubsystem;

public class ArmExtensionCommand extends CommandBase {

    private ArmExtensionSubsystem armExtensionSubsystem;
    private double setPoint;

    public ArmExtensionCommand(ArmExtensionSubsystem armExtensionSubsystem, double setPoint) {

        this.armExtensionSubsystem = armExtensionSubsystem;
        this.setPoint = setPoint;
        addRequirements(armExtensionSubsystem);
    }

    @Override
    public void initialize() {

        armExtensionSubsystem.setExtensionLength(setPoint);

        

    }
}