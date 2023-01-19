package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.DriveByController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.autos.SimpleAuto;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.JoystickAnalogButton;
import frc.robot.utilities.SwerveAlignment;

/*
* This class is where the bulk of the robot should be declared.  Since Command-based is a
* "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
* periodic methods (other than the scheduler calls).  Instead, the structure of the robot
* (including subsystems, commands, and button mappings) should be declared here
*/
public class RobotContainer {

  private SwerveAlignment swerveAlignment;

  //private final PneumaticHub pneumaticHub;

  // The robot's subsystems
  private final Drivetrain m_robotDrive;
  // private final TrackingTurretSubsystem trackingTurretSubsystem;
  // The driver's controllers
  final XboxController m_driverController;
  final XboxController m_operatorController;

  final SendableChooser<Command> m_chooser;

  private final DriveByController m_drive;
  private final ExampleCommand exampleCommand;

  private Command simpleAuto;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   *
   * @param drivetrain
   */
  public RobotContainer(Drivetrain drivetrain) {

    m_robotDrive = drivetrain;

    initializeCamera();

    m_driverController = new XboxController(OIConstants.kDriverControllerPort);
    m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);
    m_drive = new DriveByController(m_robotDrive, m_driverController);
    m_robotDrive.setDefaultCommand(m_drive); // Set drivetrain default command to "DriveByController"

    m_chooser = new SendableChooser<>();
    configureAutoChooser(drivetrain);

    swerveAlignment = new SwerveAlignment(drivetrain);

    exampleCommand = new ExampleCommand();

    configureButtonBindings(); /*
    * Configure the button bindings to commands using configureButtonBindings
    * function
    */
  }

  /**
   * Creates and establishes camera streams for the shuffleboard ~Ben
   */
  private void initializeCamera() {

    CameraServer.startAutomaticCapture();
    // VideoSource[] enumerateSources = VideoSource.enumerateSources();

    // if (enumerateSources.length > 0 && enumerateSources[0].getName().contains("USB")) {
    //   Shuffleboard.getTab("RobotData").add("Camera", enumerateSources[0]).withPosition(5, 0).withSize(3, 3)
    //       .withWidget(BuiltInWidgets.kCameraStream);
    // }
    
    HttpCamera limelight = new HttpCamera("Limelight", Configrun.get("http://10.43.29.11:5800", "Limelighturl"));
    System.out.println(Configrun.get("http://10.43.29.11:5800", "Limelighturl"));
    CameraServer.startAutomaticCapture(limelight);

    Shuffleboard.getTab("RobotData").add("Limelight Camera", limelight).withPosition(2, 0).withSize(2, 2)
        .withWidget(BuiltInWidgets.kCameraStream);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of
   * its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or
   * {@link XboxController}), and then calling passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    //Driver Controller
    new POVButton(m_driverController, 180).whenPressed(() -> m_robotDrive.resetOdometry(new Pose2d(new Translation2d(), new Rotation2d(Math.PI))));
    new POVButton(m_driverController, 0).whenPressed(() -> m_robotDrive.resetOdometry(new Pose2d(new Translation2d(), new Rotation2d(0.0))));
    new JoystickButton(m_driverController, Button.kRightBumper.value).whenPressed(() -> m_drive.changeFieldOrient());

      //Climber arm controls
    new JoystickButton(m_driverController, Button.kY.value).whenPressed(exampleCommand);
    new JoystickButton(m_driverController, Button.kX.value).whenPressed(exampleCommand);
    new JoystickButton(m_driverController, Button.kB.value).whenPressed(exampleCommand);
    new JoystickButton(m_driverController, Button.kA.value).whenHeld(exampleCommand);

      //Climber motor controls
    new JoystickAnalogButton(m_driverController, false).whenHeld(exampleCommand);
    new JoystickAnalogButton(m_driverController, true).whenHeld(exampleCommand);
    new JoystickButton(m_driverController, Button.kLeftBumper.value).whenPressed(exampleCommand);

    //Operator Controller
      //Shoot
    new JoystickButton(m_operatorController, Button.kY.value).whenHeld(exampleCommand);
    new JoystickButton(m_operatorController, Button.kBack.value).whenHeld(exampleCommand);
    new JoystickButton(m_operatorController, Button.kStart.value).whenHeld(exampleCommand);
    new JoystickButton(m_operatorController, Button.kA.value).whenHeld(exampleCommand);
      //Manage cargo
    new JoystickButton(m_operatorController, Button.kX.value).whenHeld(exampleCommand);
    new JoystickButton(m_operatorController, Button.kB.value).whenHeld(exampleCommand);
    new JoystickButton(m_operatorController, Button.kB.value).whenReleased(exampleCommand);
    new JoystickButton(m_operatorController, Button.kRightBumper.value).whenHeld(exampleCommand);
    new JoystickButton(m_operatorController, Button.kLeftBumper.value).whenHeld(exampleCommand);
  }


  /**
   * Pulls autos and configures the chooser
   */
  private void configureAutoChooser(Drivetrain drivetrain) {


    simpleAuto = new SimpleAuto(m_robotDrive);

    // Adds autos to the chooser
    m_chooser.addOption("simpleAuto", simpleAuto);


    // Puts autos on Shuffleboard
    Shuffleboard.getTab("RobotData").add("SelectAuto", m_chooser).withSize(2, 1).withPosition(0, 0);
    if (Configrun.get(false, "extraShuffleBoardToggle")) {
      Shuffleboard.getTab("Autonomous").add("Documentation",
          "Autonomous Modes at https://stem2u.sharepoint.com/sites/frc-4329/_layouts/15/Doc.aspx?sourcedoc={91263377-8ca5-46e1-a764-b9456a3213cf}&action=edit&wd=target%28Creating%20an%20Autonomous%20With%20Pathplanner%7Cb37e1a20-51ec-9d4d-87f9-886aa67fcb57%2F%29")
          .withPosition(2, 2).withSize(4, 1);
    }
  }

  /**
   * @return Selected Auto
   */
  public Command getAuto() {

    return m_chooser.getSelected();
  }

}