package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swerve.Drivetrain;

public class LimlighSubsystem extends SubsystemBase {

    NetworkTable limligh;
    GenericEntry doesthiswork;
    Drivetrain drivetrain;
    GenericEntry pose;
    double[] hrm;
    public enum Alliance {

        RED,
        BLUE
    };
    Alliance currentAlliance;

    public LimlighSubsystem(Drivetrain drivetrain) {

        limligh = NetworkTableInstance.getDefault().getTable("limelight-limligh");
        doesthiswork = Shuffleboard.getTab("ikfsdal").add("work?", 1).getEntry();
        pose = Shuffleboard.getTab("ikfsdal").add("pose?", "yes").getEntry();
        this.drivetrain = drivetrain;

        if (NetworkTableInstance.getDefault().getTable("FMSInfo").getEntry("IsRedAlliance").getBoolean(false)) {

            currentAlliance = Alliance.RED;
        } else {

            currentAlliance = Alliance.BLUE;
        }
    }

    public boolean targetVisible() {

        return limligh.getEntry("tv").getBoolean(false);
    }

    /**
     * Pulls target x (the difference as an angle with respect to x value of our robot and the target) 
     * from our limligh via networkTableEntries
     * 
     * @return the target x angle
     */
    public double getTargetx() {

        return limligh.getEntry("tx").getDouble(0);
    }

    /**
     * Pulls target y (the difference as an angle with respect to y value of our robot and the target) 
     * from our limligh via networkTableEntries
     * 
     * @return the target y angle
     */
    public double getTargety() {

        return limligh.getEntry("ty").getDouble(0);
    }

    public double getTargetId() {

        return limligh.getEntry("tid").getDouble(0);
    }

    public Pose2d hj() {

        if (Alliance.RED.equals(currentAlliance)) {

            hrm = limligh.getEntry("botpose_wpired").getDoubleArray(new double[] {0, 0, 0, 0, 0, 0});
        } else {

            hrm = limligh.getEntry("botpose_wpiblue").getDoubleArray(new double[] {0, 0, 0, 0, 0, 0});
        }

        return new Pose2d(
            new Translation2d(hrm[0], hrm[1]),
            new Rotation2d(hrm[5])
        );
    }

    @Override
    public void periodic() {

        doesthiswork.setBoolean(true);

        hj();
        pose.setString("" + Math.round(hrm[0]) + ", " + Math.round(hrm[1]) + ", " + Math.round(hrm[5]));
    }
    
}