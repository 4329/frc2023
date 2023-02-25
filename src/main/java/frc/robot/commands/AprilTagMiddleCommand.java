package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class AprilTagMiddleCommand extends CommandBase {

    LimlighSubsystem limlighSubsystem;

    PIDController centerPID;
    PIDController forwardPID;
    PIDController rotationPID;

    GenericEntry p;
    GenericEntry l;
    GenericEntry j;
    GenericEntry p2;
    GenericEntry p3;
    GenericEntry i;

    double targetId;

    Drivetrain drivetrain;

    public AprilTagMiddleCommand(LimlighSubsystem limlighSubsystem, double targetId, Drivetrain m_drivetrain) {
        
        l = Shuffleboard.getTab("sdjkfsda").add("dsakjfsd", false).getEntry();
        p = Shuffleboard.getTab("sdjkfsda").add("safds", 0.01).getEntry();
        j = Shuffleboard.getTab("sdjkfsda").add("sadkfl", 0).getEntry();
        p2 = Shuffleboard.getTab("sdjkfsda").add("safds2", -0.00).getEntry();
        p3 = Shuffleboard.getTab("sdjkfsda").add("safds3", 0).getEntry();
        i = Shuffleboard.getTab("sdjkfsda").add("sifds", 0).getEntry();
        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        centerPID = new PIDController(0.05, 0.0005, 0);
        centerPID.setTolerance(0);
        forwardPID = new PIDController(-1, 0, 0);
        this.targetId = targetId;
        rotationPID = new PIDController(0, 0, 0);
        addRequirements(limlighSubsystem, m_drivetrain);
    }

    @Override
    public void initialize() {
        

    }

    @Override
    public void execute() {
        
        centerPID = new PIDController(p.getDouble(0), i.getDouble(0), 0);
        forwardPID = new PIDController(p2.getDouble(0), 0, 0);
        rotationPID = new PIDController(p3.getDouble(0), 0, 0);

        j.setDouble(limlighSubsystem.getCalculatedPoseRot());


        if (this.limlighSubsystem.getTargetId() == targetId) {
            
            drivetrain.unlock();
            centerPID.setSetpoint(0);
            forwardPID.setSetpoint(1.88);
            rotationPID.setSetpoint(180);
            drivetrain.drive(/*forwardPID.calculate(limlighSubsystem.getCalculatedPoseZ()), centerPID.calculate(limlighSubsystem.getTargetx())*/0, 0, rotationPID.calculate(limlighSubsystem.getCalculatedPoseRot()), false);
            System.out.println("I am movin WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWw");

        } else {

            drivetrain.lock();
        }
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        
        drivetrain.unlock();
    }
    
}
