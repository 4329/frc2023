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
    Drivetrain drivetrain;
    GenericEntry pose;
    GenericEntry asdfg;
    GenericEntry jflaiss;
    double[] hrm;

    public enum Alliance {

        RED,
        BLUE
    };
    // public enum LimlighPipeline {

    //     FIDUCIAL,
    //     RETROREFLECTIVE
    // }

    Alliance currentAlliance;

    public LimlighSubsystem(Drivetrain drivetrain) {

        limligh = NetworkTableInstance.getDefault().getTable("limelight-limligh");
        pose = Shuffleboard.getTab("ikfsdal").add("pose?", "yes").getEntry();
        asdfg = Shuffleboard.getTab("ikfsdal").add("targetZ?", 0).getEntry();
        this.drivetrain = drivetrain;

        if (NetworkTableInstance.getDefault().getTable("FMSInfo").getEntry("IsRedAlliance").getBoolean(false)) {

            currentAlliance = Alliance.RED;
        } else {

            currentAlliance = Alliance.BLUE;
        }
        // jflaiss = Shuffleboard.getTab("ikfsdal").add("pipeline", 0).getEntry();
    }

    public boolean targetVisible() {

        return limligh.getEntry("tv").getDouble(0) == 1;
    }

    /**
     * Pulls target x (the difference as an angle with respect to x value of our
     * robot and the target)
     * from our limligh via networkTableEntries
     * 
     * @return the target x angle
     */
    public double getTargetx() {

        return limligh.getEntry("tx").getDouble(0);
    }

    public double getCalculatedPoseZ() {

        return getPose().getX();
    }

    public double getCalculatedPoseRot() {

        return getPose().getRotation().getDegrees();
    }

    /**
     * Pulls target y (the difference as an angle with respect to y value of our
     * robot and the target)
     * from our limligh via networkTableEntries
     * 
     * @return the target y angle
     */
    public double getTargety() {
        
        return limligh.getEntry("ty").getDouble(0);
    }

    /**
     * gives how much of the camera a target covers
     * 
     * @return target area
     */
    public double getTargeta() {

        return limligh.getEntry("ta").getDouble(0);
    }

    public double getTargetId() {

        return limligh.getEntry("tid").getDouble(0);
    }

    public Pose2d getPose() {

        // if (Alliance.RED.equals(currentAlliance)) {

        //     hrm = limligh.getEntry("botpose_wpired").getDoubleArray(new double[] {0, 0, 0, 0, 0, 0});
        // } else {

        //     hrm = limligh.getEntry("botpose_wpiblue").getDoubleArray(new double[] {0, 0, 0, 0, 0, 0});
        // }

        hrm = limligh.getEntry("botpose").getDoubleArray(new double[] {0, 0, 0, 0, 0, 0});

        return new Pose2d(
            
            new Translation2d(hrm[0], hrm[1]),
            Rotation2d.fromDegrees(hrm[5])
        );
    }

    public void switchPipeline(double pipeline) {

        limligh.getEntry("pipeline").setDouble(pipeline);
    }

    public double getPipeline() {

        return limligh.getEntry("pipeline").getDouble(0);
    }


    @Override
    public void periodic() {

        // //getPose();
        // pose.setString("" + (hrm[0]) + ", " + (hrm[1]) + ", " + (hrm[5]));
        // asdfg.setDouble(getCalculatedPoseZ());
        // // switchPipeline(LimlighPipeline.values()[Math.toIntExact(jflaiss.getInteger(0))]);
    }

}