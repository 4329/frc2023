package frc.robot.commands.extend;

import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SafeExtendCommand extends CommandBase {

    ArmRotationSubsystem armRotationSubsystem;

    public SafeExtendCommand(ArmRotationSubsystem armRotationSubsystem) {

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