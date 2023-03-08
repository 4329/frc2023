package frc.robot.commands.rotation;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SafeRotateCommand extends CommandBase {

    ArmRotationSubsystem armRotationSubsystem;

    public SafeRotateCommand(ArmRotationSubsystem armRotationSubsystem) {

        this.armRotationSubsystem = armRotationSubsystem;
    }

    @Override
    public void execute() {

        if (armRotationSubsystem.isLowerThanSafeExtend()) {

            armRotationSubsystem.setArmPosition(ArmHeight.SAFEEXTEND);
        }
    }

    @Override
    public boolean isFinished() {
       
       if (!armRotationSubsystem.isLowerThanSafeExtend()) {

            return true;
       } else {

            return armRotationSubsystem.armAtSetpoint();
       }
    }

}