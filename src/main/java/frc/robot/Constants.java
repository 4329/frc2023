package frc.robot;

import java.awt.geom.Point2D;

import frc.robot.utilities.HoorayConfig;
import frc.robot.utilities.LinearInterpolationTable;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

/**
 * Static method containing all constant values for the robot in one location
 */
public final class Constants {

  /**
   * Static method containing all Drivetrain constants
   */
  public static final class DriveConstants {

    public static final double kVoltCompensation = 12.0; // Sets a voltage compensation value ideally 12.0V
    public static final double kLoopTime = 20.0;

    public static final int kFrontLeftDriveMotorPort = (HoorayConfig.gimmeConfig().getFrontLeftDriveMotorPort()); // CANID of the
                                                                                                      // Translation
                                                                                                      // SparkMAX
    public static final int kFrontRightDriveMotorPort = (HoorayConfig.gimmeConfig().getFrontRightDriveMotorPort()); // CANID of the
                                                                                                        // Translation
                                                                                                        // SparkMAX
    public static final int kBackLeftDriveMotorPort = (HoorayConfig.gimmeConfig().getBackLeftDriveMotorPort()); // CANID of the
                                                                                                    // Translation
                                                                                                    // SparkMAX
    public static final int kBackRightDriveMotorPort = (HoorayConfig.gimmeConfig().getBackRightDriveMotorPort()); // CANID of the
                                                                                                      // Translation
                                                                                                      // SparkMAX
    public static final int kFrontLeftTurningMotorPort = (HoorayConfig.gimmeConfig().getFrontLeftTurningMotorPort()); // CANID of
                                                                                                          // the
                                                                                                          // Rotation
                                                                                                          // SparkMAX
    public static final int kFrontRightTurningMotorPort = (HoorayConfig.gimmeConfig().getFrontRightTurningMotorPort()); // CANID of
                                                                                                            // the
                                                                                                            // Rotation
                                                                                                            // SparkMAX
    public static final int kBackLeftTurningMotorPort = (HoorayConfig.gimmeConfig().getBackLeftTurningMotorPort()); // CANID of the
                                                                                                        // Rotation
                                                                                                        // SparkMAX
    public static final int kBackRightTurningMotorPort = (HoorayConfig.gimmeConfig().getBackRightTurningMotorPort()); // CANID of
                                                                                                          // the
                                                                                                          // Rotation
                                                                                                          // SparkMAX

    public static final int kFrontLeftTurningEncoderPort = (HoorayConfig.gimmeConfig().getFrontLeftTurningEncoderPort());
    public static final int kFrontRightTurningEncoderPort = (HoorayConfig.gimmeConfig().getFrontRightTurningEncoderPort());
    public static final int kBackLeftTurningEncoderPort = (HoorayConfig.gimmeConfig().getBackLeftTurningEncoderPort());
    public static final int kBackRightTurningEncoderPort = (HoorayConfig.gimmeConfig().getBackRightTurningEncoderPort());

    public static final double kFrontLeftOffset = HoorayConfig.gimmeConfig().getFrontLeftOffset(); // Encoder Offset in Radians
    public static final double kFrontRightOffset = HoorayConfig.gimmeConfig().getFrontRightOffset(); // Encoder Offset in
                                                                                                // Radians
    public static final double kBackLeftOffset = HoorayConfig.gimmeConfig().getBackLeftOffset(); // Encoder Offset in Radians
    public static final double kBackRightOffset = HoorayConfig.gimmeConfig().getBackRightOffset(); // Encoder Offset in Radians

    public static final double[] kFrontLeftTuningVals = { 0.0150, 0.2850, 0.25, 0 };
    public static final double[] kFrontRightTuningVals = { 0.0150, 0.2850, 0.25, 1 };
    public static final double[] kBackLeftTuningVals = { 0.0150, 0.2850, 0.25, 2 };
    public static final double[] kBackRightTuningVals = { 0.0150, 0.2850, 0.25, 3 };

    // NOTE: 2910 Swerve the wheels are not directly under the center of rotation
    // (Take into consideration when measuring)
    // Center distance in meters between right and left wheels on robot
    public static final double kWheelBaseWidth = 0.5207;
    // Center distance in meters between front and back wheels on robot
    public static final double kWheelBaseLength = 0.644525;

    // Because the swerve modules poisition does not change, define a constant
    // SwerveDriveKinematics for use throughout the code
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBaseLength / 2, kWheelBaseWidth / 2),
        new Translation2d(kWheelBaseLength / 2, -kWheelBaseWidth / 2),
        new Translation2d(-kWheelBaseLength / 2, kWheelBaseWidth / 2),
        new Translation2d(-kWheelBaseLength / 2, -kWheelBaseWidth / 2));

    public static final double kMaxAcceleration = 3.0;
    public static final double kMaxSpeedMetersPerSecond = 3.25;
    public static final double kMaxAngularSpeed = Math.PI;
    public static final double kMaxAngularAccel = Math.PI;

    public static final double kInnerDeadband = 0.10;
    public static final double kOuterDeadband = 0.98;

    // Minimum allowable rotation command (in radians/s) assuming user input is
    // squared using quadraticTransform, this value is always positive and should be
    // compared agaisnt the absolute value of the drive command
    public static final double kMinRotationCommand = DriveConstants.kMaxAngularSpeed
        * Math.pow(DriveConstants.kInnerDeadband, 2);
    // Minimum allowable tranlsation command (in m/s) assuming user input is squared
    // using quadraticTransform, this value is always positive and should be
    // compared agaisnt the absolute value of the drive command
    public static final double kMinTranslationCommand = DriveConstants.kMaxSpeedMetersPerSecond
        * Math.pow(DriveConstants.kInnerDeadband, 2);

    public static final double[] kKeepAnglePID = { 0.550, 0, 0 };

    public static final double maxRampRoll = 15;
    public static final double maxRampDeviation = 0.1;
    public static final double maxRampSpeed = 0.5;
  }

  /**
   * Static method containing all Swerve Module constants
   */
  public static final class ModuleConstants {
    // Units of %power/s, ie 4.0 means it takes 0.25s to reach 100% power from 0%
    public static final double kTranslationRampRate = 4.0;
    private static final double kTranslationGearRatio = 8.33333333;

    private static final double kWheelDiameter = 0.09845; // Wheel Diameter in meters

    public static final double kVelocityFactor = (1.0 / kTranslationGearRatio / 60.0) * kWheelDiameter * Math.PI;

    // NOTE: You shoulds ALWAYS define a reasonable current limit when using
    // brushless motors due to the extremely high stall current avaialble
    public static final int kDriveCurrentLimit = 30;
    public static final int kTurnCurrentLimit = 25;

    public static final double[] kTurnPID = { 0.600, 0, 0 }; // should show some minor oscillation when no weight is
                                                             // loaded on the modules
  }

  /**
   * Static method containing all User O/I constants
   */
  public static final class OIConstants {

    public static final int kDriverControllerPort = 0; // When making use of multiple controllers for drivers each
                                                       // controller will be on a different port
    public static final int kOperatorControllerPort = 1; // When making use of multiple controllers for drivers each
                                                         // controller will be on a different port
  }

  /**
   * Static method containing all Autonomous constants
   */
  public static final class AutoConstants {
    public static final double kMaxAcceleration = 2.5;
    public static final double kMaxSpeed = 3.25; // Maximum Sustainable Drivetrain Speed under Normal Conditions &
                                                 // Battery, Robot will not exceed this speed in closed loop control
    public static final double kMaxAngularSpeed = Math.PI; // Maximum Angular Speed desired. NOTE: Robot can exceed this
                                                           // but spinning fast is not particularly useful or driver
                                                           // friendly
    public static final double kMaxAngularAccel = Math.PI; // Maximum Angular Speed desired. NOTE: Robot can exceed this
                                                           // but spinning fast is not particularly useful or driver
                                                           // friendly

    public static final double kPXController = 0.001;
    public static final double kPYController = 0.001;
    public static final double kPThetaController = 2.0;

    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeed, kMaxAngularAccel); // Creates a trapezoidal motion for the auto rotational commands
  }

  public static final class ColorConstants {

    public static final double confidenceLevel = 1;
  }

  public static final class ArmExtendConstants {

    public static final double armExtendSpeed = 0.05;
  }

  public static final class ArmRotationConstants {

    public static final double armRotateSpeed = 0.03;
  }

  public static final class WristConstants {
    
    public static final double wristRotationSpeed = 1;
  }

  public static final class CANIDConstants {

    public static final int clawRight = 13;
    public static final int clawLeft = 14;
    public static final int armRotation1 = 15;
    public static final int armRotation2 = 16;
    public static final int armExtension = 20;
    public static final int wristRotate = 21;
  }

}