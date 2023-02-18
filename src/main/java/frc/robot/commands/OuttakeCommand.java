package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public class OuttakeCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private ArmRotationSubsystem armRotationSubsystem;

    public OuttakeCommand(ClawSubsystem clawSubsystem, ArmRotationSubsystem armRotationSubsystem) {

        this.clawSubsystem = clawSubsystem;
        this.armRotationSubsystem = armRotationSubsystem;
        addRequirements(clawSubsystem);
    }

    @Override
    public void execute() {

        if (ArmHeight.HIGH.equals(armRotationSubsystem.getArmPosition())) {

            clawSubsystem.outtakeHigh();

        } else if (ArmHeight.MID.equals(armRotationSubsystem.getArmPosition())) {

            clawSubsystem.outtakeMid();
        } else if (ArmHeight.LOW.equals(armRotationSubsystem.getArmPosition())) {

            clawSubsystem.outtakeLow();
        }


















        clawSubsystem.outtakeHigh();
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        
        clawSubsystem.stop();
    }

}
