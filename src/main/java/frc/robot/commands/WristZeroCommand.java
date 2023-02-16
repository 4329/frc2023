package frc.robot.commands;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class WristZeroCommand extends WristToPositionCommand {

    ArmRotationSubsystem armRotationSubsystem;

    public WristZeroCommand(WristSubsystem wristSubsystem, ArmRotationSubsystem armRotationSubsystem) {

        super(wristSubsystem, 0);
        this.armRotationSubsystem = armRotationSubsystem;
    }

    @Override
    public void execute() {

        super.execute();
        armRotationSubsystem.stepNum.setDouble(1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    
}
