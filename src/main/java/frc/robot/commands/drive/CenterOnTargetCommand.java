package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;

public class CenterOnTargetCommand extends CommandBase {

    LimlighSubsystem limlighSubsystem;

    PIDController centerPID;
    PIDController forwardPID;
    PIDController rotationPID;

    double centerCalc;
    double forwardCalc;
    double rotationCalc;

    double targetId;

    Drivetrain drivetrain;

    public CenterOnTargetCommand(LimlighSubsystem limlighSubsystem, double targetId, Drivetrain m_drivetrain) {

        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        this.targetId = targetId;

        centerPID = new PIDController(0.01, 0, 0);
        centerPID.setTolerance(1);

        forwardPID = new PIDController(0.5, 0, 0);
        forwardPID.setTolerance(0.5);

        rotationPID = new PIDController(0.01, 0, 0);
        rotationPID.setTolerance(0.5);

        addRequirements(limlighSubsystem, m_drivetrain);
    }

    @Override
    public void initialize() {

        centerPID.setSetpoint(-4);
        forwardPID.setSetpoint(14.65);
        rotationPID.setSetpoint(7);
    }

    @Override
    public void execute() {

        if (limlighSubsystem.getPipeline() == 0 && limlighSubsystem.getTargetId() == targetId) {

            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx(), -4);
            centerPID.setTolerance(0.5);
            centerPID.setP(0.03);
            centerPID.setD(0);

            forwardCalc = forwardPID.calculate(limlighSubsystem.getCalculatedPoseZ(), 14.65);
            forwardPID.setP(1);
            forwardPID.setTolerance(0.5);

            rotationCalc = rotationPID.calculate(limlighSubsystem.getCalculatedPoseRot(), 7);
            rotationPID.setP(0.03);
            rotationPID.setTolerance(0.75);

        } else if (limlighSubsystem.getPipeline() == 1 && limlighSubsystem.targetVisible()) {

            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx(), 0);
            centerPID.setTolerance(0.5);
            centerPID.setP(0.05);
            centerPID.setD(0.001);

            forwardCalc = forwardPID.calculate(limlighSubsystem.getTargeta(), 0.27);
            forwardPID.setP(2);
            forwardPID.setTolerance(0.05);

            rotationCalc = rotationPID.calculate(drivetrain.getGyro().getDegrees(), 0);
            rotationPID.setP(0.01);
            rotationPID.setTolerance(5);
        }

        if ((!centerPID.atSetpoint() || !rotationPID.atSetpoint() || !forwardPID.atSetpoint())) {

            drivetrain.unlock();
            drivetrain.drive(forwardCalc, centerCalc, rotationCalc, false);
        } else {

            drivetrain.lock();
        }

    }

    @Override
    public boolean isFinished() {

        return centerPID.atSetpoint() && rotationPID.atSetpoint() && forwardPID.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.unlock();
    }

}
