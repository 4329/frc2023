package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.MathUtils;

public class CenterOnRetroreflectiveCommand extends CommandBase {

    private final LimlighSubsystem limlighSubsystem;
    private final Drivetrain drivetrain;
    private final PIDController rotationPID;
    private CommandXboxController xboxController;

    public CenterOnRetroreflectiveCommand(LimlighSubsystem limlighSubsystem, Drivetrain m_drivetrain, CommandXboxController xboxController) {
        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        this.xboxController = xboxController;
        rotationPID = new PIDController(0.05, 0, 0);
        rotationPID.setTolerance(0.65);
        rotationPID.setSetpoint(0);
        addRequirements(limlighSubsystem, m_drivetrain);
    }

    @Override
    public void initialize() {

        limlighSubsystem.switchPipeline(1);
    }

    @Override
    public void execute() {

        double rotationCalc = 0;
        rotationCalc = rotationPID.calculate(limlighSubsystem.getTargetx(), 0);

        if (rotationCalc > Constants.DriveConstants.kMaxAngularSpeed) {

            rotationCalc = Constants.DriveConstants.kMaxAngularSpeed;
        } else if (rotationCalc < -Constants.DriveConstants.kMaxAngularSpeed) {

            rotationCalc = -Constants.DriveConstants.kMaxAngularSpeed;
        } else if (rotationPID.atSetpoint()) {

            rotationCalc = 0;
        }

        double adjTranslation = ((Constants.DriveConstants.kMaxAngularSpeed - Math.abs(rotationCalc))
            / Constants.DriveConstants.kMaxAngularSpeed) * 0.5;

        drivetrain.drive(

            -inputTransform(xboxController.getLeftY())
                * (Constants.DriveConstants.kMaxSpeedMetersPerSecond * adjTranslation),
            -inputTransform(xboxController.getLeftX())
                * (Constants.DriveConstants.kMaxSpeedMetersPerSecond * adjTranslation),
            rotationCalc,
            true
        );
    }

    @Override
    public boolean isFinished() {
        
        System.out.println(rotationPID.atSetpoint() + "fjskadjfljsdaljflsajlkfjijifjewjlifjilsdjalfjlsdij");
        return rotationPID.atSetpoint() && limlighSubsystem.targetVisible();
    }

    @Override
    public void end(boolean interrupted) {
        
        drivetrain.stop();
        drivetrain.unlock();
        limlighSubsystem.switchPipeline(0);
    }

    private double inputTransform(double input) {
        
        return MathUtils.singedSquare(MathUtils.applyDeadband(input));
    }

}