package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;
import frc.robot.subsystems.ColorDetector.FieldElement;

public class OuttakeCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private ArmRotationSubsystem armRotationSubsystem;
    ColorDetector colorDetector;

    FieldElement elly;

    public OuttakeCommand(ClawSubsystem clawSubsystem, ArmRotationSubsystem armRotationSubsystem, ColorDetector colorDetector) {

        this.clawSubsystem = clawSubsystem;
        this.armRotationSubsystem = armRotationSubsystem;
        this.colorDetector = colorDetector;
        addRequirements(clawSubsystem);
    }

    @Override
    public void initialize() {

        elly = colorDetector.detectElement();
    }

    @Override
    public void execute() {

        if (ArmHeight.HIGH.equals(armRotationSubsystem.getArmPosition())) {

            clawSubsystem.outtakeHigh(elly);
        } else if (ArmHeight.MID.equals(armRotationSubsystem.getArmPosition())) {

            clawSubsystem.outtakeMid(elly);
        } else if (ArmHeight.LOW.equals(armRotationSubsystem.getArmPosition())) {

            clawSubsystem.outtakeLow(elly);
        }
    }

    @Override
    public void end(boolean interrupted) {
        
        clawSubsystem.stop();
    }

}
