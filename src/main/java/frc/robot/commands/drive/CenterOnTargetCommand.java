package frc.robot.commands.drive;

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
    GenericEntry djaslk;
    GenericEntry[] ack;

    double centerCalc;
    double forwardCalc;
    double rotationCalc;

    double targetId;

    Drivetrain drivetrain;

    GenericEntry ha;
    
    GenericEntry noo;
    GenericEntry asd;
    GenericEntry ithurts;
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
        ha = Shuffleboard.getTab("ikfsdal").add("laksajklfjsaldfjljsd", 0.75).getEntry();
        djaslk = Shuffleboard.getTab("ikfsdal").add("ack", 0).getEntry();
        ack = new GenericEntry[] {

            Shuffleboard.getTab("ikfsdal").add("p1", 0.05).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("p2", 2).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("p3", 0.01).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("t1", 0.5).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("t2", 0.05).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("t3", 5).getEntry()
        };
        asd = Shuffleboard.getTab("ikfsdal").add("morepain", 10).getEntry();
        noo = Shuffleboard.getTab("ikfsdal").add("noo", 0).getEntry();
        ithurts = Shuffleboard.getTab("ikfsdal").add("ithurts", false).getEntry();
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

            centerPID.setP(0.04);
            centerPID.setD(0);
            centerPID.setTolerance(0.5);
            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx(), -4);

            forwardPID.setP(1.7);
            forwardPID.setI(0);
            forwardPID.setD(0);
            forwardPID.setTolerance(0.7);
            forwardCalc = forwardPID.calculate(limlighSubsystem.getCalculatedPoseZ(), 14.65);

            rotationPID.setP(0.03);
            rotationPID.setTolerance(0.7);
            rotationCalc = rotationPID.calculate(limlighSubsystem.getCalculatedPoseRot(), 7);

        } else if (limlighSubsystem.getPipeline() == 1 && limlighSubsystem.targetVisible()) {

            centerPID.setP(ack[0].getDouble(0));
            centerPID.setD(0.001);
            centerPID.setTolerance(ack[3].getDouble(0));
            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx(), 0);

            forwardPID.setP(ack[1].getDouble(0));
            forwardPID.setI(0);
            forwardPID.setD(0);
            forwardPID.setTolerance(ack[4].getDouble(0));
            forwardCalc = forwardPID.calculate(limlighSubsystem.getTargeta(), 0.27);

            rotationPID.setP(asd.getDouble(0));
            rotationPID.setTolerance(0);
            rotationCalc = rotationPID.calculate(drivetrain.getGyro().getDegrees(), 0);
            
        }
        djaslk.setDouble(rotationCalc);
        noo.setDouble(rotationPID.getP());

        if ((!centerPID.atSetpoint() || !rotationPID.atSetpoint() || !forwardPID.atSetpoint())) {

            drivetrain.unlock();
            drivetrain.drive(forwardCalc, centerCalc, rotationCalc, false);
            ithurts.setBoolean(true);
        } else {

            drivetrain.lock();
            ithurts.setBoolean(false);
        }

    }

    @Override
    public boolean isFinished() {

        return centerPID.atSetpoint() && rotationPID.atSetpoint() && forwardPID.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.unlock();
        // djaslk.setBoolean(false);
    }

}
