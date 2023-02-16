package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class HighArmCommand extends MoveArmCommand {

    ArmRotationSubsystem armRotationSubsystem;

    public HighArmCommand(ArmRotationSubsystem armRotationSubsystem) {
        
        super(armRotationSubsystem, ArmHeight.HIGH);
        this.armRotationSubsystem = armRotationSubsystem;

    }
    @Override
    public void execute() {
        super.execute();

        armRotationSubsystem.stepNum.setDouble(2);
    }
}