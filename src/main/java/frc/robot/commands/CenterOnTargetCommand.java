package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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

    GenericEntry dsaljfk;
    GenericEntry lk;
    GenericEntry asdklf;

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

        dsaljfk = Shuffleboard.getTab("ikfsdal").add("hahaha", false).getEntry();
        lk = Shuffleboard.getTab("ikfsdal").add("kakakak", 4).getEntry();
        asdklf = Shuffleboard.getTab("ikfsdal").add("llalala", 0.05).getEntry();
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
            centerPID.setP(0.01);

            forwardCalc = forwardPID.calculate(limlighSubsystem.getCalculatedPoseZ(), 14.65);
            forwardPID.setP(0.5);
            forwardPID.setTolerance(0.5);

            rotationCalc = rotationPID.calculate(limlighSubsystem.getCalculatedPoseRot(), 7);
        } else if (limlighSubsystem.getPipeline() == 1 && limlighSubsystem.targetVisible()) {

            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx(), 0);
            centerPID.setP(0.005);
            centerPID.setTolerance(1.5);

            forwardCalc = forwardPID.calculate(limlighSubsystem.getTargeta(), 0.27);
            forwardPID.setP(0.75);
            forwardPID.setTolerance(0.075);

            rotationCalc = rotationPID.calculate(drivetrain.getGyro().getDegrees(), 0);
            dsaljfk.setBoolean(true);
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
