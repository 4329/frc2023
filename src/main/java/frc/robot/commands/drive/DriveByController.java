package frc.robot.commands.drive;

import frc.robot.Constants.*;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.swerve.*;
import frc.robot.utilities.MathUtils;
import edu.wpi.first.wpilibj.XboxController;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * Implements a DriveByController command which extends the CommandBase class
 */
public class DriveByController extends CommandBase {
  private final Drivetrain m_robotDrive;
  private final CommandXboxController m_controller;
  private boolean fieldOrient = true;
  private final ArmRotationSubsystem m_armRotationSubsystem;
  private GenericEntry fieldOrientStatus = Shuffleboard.getTab("RobotData").add("Field Orient On", true)
      .withProperties(Map.of("Color when true", "#FFFFFF", "Color when false", "#000000")).withSize(3, 3)
      .withPosition(0, 2).getEntry();

  /**
   * Contructs a DriveByController object which applys the driver inputs from the
   * controller to the swerve drivetrain
   * 
   * @param drive      is the swerve drivetrain object which should be created in
   *                   the RobotContainer class
   * @param controller is the user input controller object for controlling the
   *                   drivetrain
   */
  public DriveByController(Drivetrain drive, CommandXboxController controller,
      ArmRotationSubsystem armRotationSubsystem) {
    m_robotDrive = drive; // Set the private member to the input drivetrain
    m_controller = controller; // Set the private member to the input controller
    m_armRotationSubsystem = armRotationSubsystem;
    addRequirements(m_robotDrive); // Because this will be used as a default command, add the subsystem which will
                                   // use this as the default
  }

  /**
   * the execute function is overloaded with the function to drive the swerve
   * drivetrain;
   */
  @Override
  public void execute() {
    double armSafetySpeed = 0;
    if (ArmRotationSubsystem.ArmHeight.ZERO.equals(m_armRotationSubsystem.getArmSetpointEnum())) {
    armSafetySpeed = 1;
    } else {
      armSafetySpeed = Math.PI / 10;
      System.out.println("were slow and stjdsjflsjlfiaj");
    }
//:3
    m_robotDrive.drive(
        -inputTransform(m_controller.getLeftY())
            * DriveConstants.kMaxSpeedMetersPerSecond * armSafetySpeed,
        -inputTransform(m_controller.getLeftX())
            * DriveConstants.kMaxSpeedMetersPerSecond * armSafetySpeed,
        -inputTransform(m_controller.getRightX())
            * DriveConstants.kMaxAngularSpeed * armSafetySpeed,
        fieldOrient);
  }

  /**
   * when this fucntion of the command is called the current fieldOrient boolean
   * is flipped. This
   * is fed into the drive command for the swerve drivetrain so the driver can
   * decide to drive in
   * a robot oreinted when they please (not recommended in most instances)
   */
  public void changeFieldOrient() {
    if (fieldOrient == true) {
      fieldOrient = false;
      fieldOrientStatus.setBoolean(false);
    } else {
      fieldOrient = true;
      fieldOrientStatus.setBoolean(true);
    }
  }

  /**
   * This function takes the user input from the controller analog sticks, applys
   * a deadband and then quadratically
   * transforms the input so that it is easier for the user to drive, this is
   * especially important on high torque motors
   * such as the NEOs or Falcons as it makes it more intuitive and easier to make
   * small corrections
   * 
   * @param input is the input value from the controller axis, should be a value
   *              between -1.0 and 1.0
   * @return the transformed input value
   */
  private double inputTransform(double input) {
    return MathUtils.singedSquare(MathUtils.applyDeadband(input));
  }

}
