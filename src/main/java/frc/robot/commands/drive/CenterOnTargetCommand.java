package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;

public class CenterOnTargetCommand extends CommandBase {

    private final LimlighSubsystem limlighSubsystem;
    private final Drivetrain drivetrain;
    private final double targetId;

    private final PIDController centerPID;
    private final PIDController forwardPID;
    private final PIDController rotationPID;

    private double centerCalc;
    private double forwardCalc;
    private double rotationCalc;

    GenericEntry jfdsoujfsadoiijofdsa;
    GenericEntry[] nooooooo;
GenericEntry pointset;


    private boolean usingAprilTag;

    public CenterOnTargetCommand(LimlighSubsystem limlighSubsystem, Drivetrain m_drivetrain, double targetId) {

        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        this.targetId = targetId;

        centerPID = new PIDController(0, 0, 0);
        forwardPID = new PIDController(0, 0, 0);
        rotationPID = new PIDController(0, 0, 0);

        addRequirements(limlighSubsystem, m_drivetrain);

        jfdsoujfsadoiijofdsa = Shuffleboard.getTab("ikfsdal").add("painhahah", false).getEntry();

        nooooooo = new GenericEntry[] {

            Shuffleboard.getTab("ikfsdal").add("p1", 0.04).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("p2", 1.7).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("p3", 0.03).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("t1", 0.5).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("t2", 0.7).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("t3", 0.7).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("s1", -4).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("s2", 4).getEntry(),
            Shuffleboard.getTab("ikfsdal").add("s3", 7).getEntry()
        };
        pointset = Shuffleboard.getTab("ikfsdal").add("at", false).getEntry();
    }

    @Override
    public void initialize() {

        if (limlighSubsystem.getPipeline() == 0) {

            usingAprilTag = true;

            centerPID.setP(nooooooo[0].getDouble(0));
            centerPID.setTolerance(nooooooo[3].getDouble(0));
            centerPID.setSetpoint(nooooooo[6].getDouble(0));

            forwardPID.setP(nooooooo[1].getDouble(0));
            forwardPID.setTolerance(nooooooo[4].getDouble(0));
            forwardPID.setSetpoint(nooooooo[7].getDouble(0));

            rotationPID.setP(nooooooo[2].getDouble(0));
            rotationPID.setTolerance(nooooooo[5].getDouble(0));
            rotationPID.setSetpoint(nooooooo[8].getDouble(0));
        } else {

            usingAprilTag = false;

            centerPID.setP(0.05);
            centerPID.setTolerance(0.4);
            centerPID.setSetpoint(0);

            forwardPID.setP(5);
            forwardPID.setTolerance(0.015);
            forwardPID.setSetpoint(0.085);

            rotationPID.setP(0.1);
            rotationPID.setTolerance(4);
            rotationPID.setSetpoint(0);
        }
    }

    @Override
    public void execute() {

        if ((limlighSubsystem.getTargetId() == targetId && usingAprilTag)) {

            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx());
            forwardCalc = forwardPID.calculate(limlighSubsystem.getCalculatedPoseZ());
            rotationCalc = rotationPID.calculate(limlighSubsystem.getCalculatedPoseRot());

            jfdsoujfsadoiijofdsa.setBoolean(true);
        } else if (limlighSubsystem.targetVisible()) {

            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx());
            forwardCalc = forwardPID.calculate(limlighSubsystem.getTargeta());
            rotationCalc = rotationPID.calculate(drivetrain.getGyro().getDegrees());

            jfdsoujfsadoiijofdsa.setBoolean(false);
        }

        if ((!centerPID.atSetpoint() || !rotationPID.atSetpoint() || !forwardPID.atSetpoint())) {

            drivetrain.unlock();
            drivetrain.drive(forwardCalc, centerCalc, rotationCalc, false);
            pointset.setBoolean(false);
        } else {

            drivetrain.lock();
            pointset.setBoolean(true);
        }
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.unlock();
        jfdsoujfsadoiijofdsa.setBoolean(false);
    }

}
