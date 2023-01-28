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
import frc.robot.commands.ArmToPositionCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.ChangeFieldOrientCommand;
import frc.robot.commands.DriveByController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.PinchCommand;
import frc.robot.commands.ReleaseCommand;
import frc.robot.commands.ResetOdometryCommand;
import frc.robot.commands.autos.SimpleAuto;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.JoystickAnalogButton;
import frc.robot.utilities.SwerveAlignment;

/* (including subsystems, commands, and button mappings) should be declared here
*/
public class RobotContainer {

  private SwerveAlignment swerveAlignment;

  // private final PneumaticHub pneumaticHub;

  // The robot's subsystems
  private final Drivetrain m_robotDrive;
  // private final TrackingTurretSubsystem trackingTurretSubsystem;
  // The driver's controllers
  final XboxController m_driverController;
  final XboxController m_operatorController;

  final SendableChooser<Command> m_chooser;

  private final DriveByController m_drive;
  private final ExampleCommand exampleCommand;
  private final ResetOdometryCommand resetOdometryCommandForward;
  private final ResetOdometryCommand resetOdometryCommandBackward;
  private final ChangeFieldOrientCommand changeFieldOrientCommand;
  private final BalanceCommand balanceCommand;
  private final MoveArmCommand armToFifty;
  private final ArmSubsystem armSubsystem;
  private final ClawSubsystem clawSubsystem;
  private final IntakeCommand intakeCommand;
  private final OuttakeCommand outtakeCommand;
  private final PinchCommand pinchCommand;
  private final ReleaseCommand releaseCommand;

  private Command simpleAuto;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   *
   * @param drivetrain
   */
  public RobotContainer(Drivetrain drivetrain) {

    m_robotDrive = drivetrain;

    initializeCamera();

    armSubsystem = new ArmSubsystem();
    m_driverController = new XboxController(OIConstants.kDriverControllerPort);
    m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);
    m_drive = new DriveByController(m_robotDrive, m_driverController);
    m_robotDrive.setDefaultCommand(m_drive); // Set drivetrain default command to "DriveByController"

    m_chooser = new SendableChooser<>();
    configureAutoChooser(drivetrain);

    swerveAlignment = new SwerveAlignment(drivetrain);

    exampleCommand = new ExampleCommand();
    resetOdometryCommandForward = new ResetOdometryCommand(new Pose2d(new Translation2d(), new Rotation2d(Math.PI)),
        drivetrain);
    resetOdometryCommandBackward = new ResetOdometryCommand(new Pose2d(new Translation2d(), new Rotation2d(0.0)),
        drivetrain);
    changeFieldOrientCommand = new ChangeFieldOrientCommand(m_drive);
    balanceCommand = new BalanceCommand(drivetrain);
    armToFifty = new MoveArmCommand(armSubsystem, 50);

    clawSubsystem = new ClawSubsystem();
    intakeCommand = new IntakeCommand(clawSubsystem);
    outtakeCommand = new OuttakeCommand(clawSubsystem);
    pinchCommand = new PinchCommand(clawSubsystem);
    releaseCommand = new ReleaseCommand(clawSubsystem);

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

    // if (enumerateSources.length > 0 &&
    // enumerateSources[0].getName().contains("USB")) {
    // Shuffleboard.getTab("RobotData").add("Camera",
    // enumerateSources[0]).withPosition(5, 0).withSize(3, 3)
    // .withWidget(BuiltInWidgets.kCameraStream);
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

    // Driver Controller
    new JoystickAnalogButton(m_driverController, true).whileTrue(exampleCommand);
    new JoystickAnalogButton(m_driverController, false).whileTrue(exampleCommand);

    new JoystickButton(m_driverController, Button.kLeftBumper.value).onTrue(exampleCommand);
    new JoystickButton(m_driverController, Button.kRightBumper.value).onTrue(changeFieldOrientCommand);

    new JoystickButton(m_driverController, Button.kStart.value).whileTrue(exampleCommand);
    new JoystickButton(m_driverController, Button.kBack.value).whileTrue(exampleCommand);

    new JoystickButton(m_driverController, Button.kA.value).whileTrue(exampleCommand);
    new JoystickButton(m_driverController, Button.kB.value).whileTrue(exampleCommand);
    new JoystickButton(m_driverController, Button.kX.value).whileTrue(exampleCommand);
    new JoystickButton(m_driverController, Button.kY.value).whileTrue(balanceCommand);

    new POVButton(m_driverController, 180).onTrue(resetOdometryCommandForward);
    new POVButton(m_driverController, 0).onTrue(resetOdometryCommandBackward);

    // Operator Controller
    new JoystickAnalogButton(m_operatorController, true).whileTrue(exampleCommand);
    new JoystickAnalogButton(m_operatorController, false).whileTrue(exampleCommand);

    new JoystickButton(m_operatorController, Button.kLeftBumper.value).whileTrue(pinchCommand);
    new JoystickButton(m_operatorController, Button.kRightBumper.value).whileTrue(releaseCommand);

    new JoystickButton(m_operatorController, Button.kStart.value).whileTrue(exampleCommand);
    new JoystickButton(m_operatorController, Button.kBack.value).whileTrue(exampleCommand);

    new JoystickButton(m_operatorController, Button.kA.value).onTrue(armToFifty);
    new JoystickButton(m_operatorController, Button.kB.value).onTrue(new ArmToPositionCommand(armSubsystem, 0));
    new JoystickButton(m_operatorController, Button.kX.value).whileTrue(intakeCommand);
    new JoystickButton(m_operatorController, Button.kY.value).whileTrue(outtakeCommand);
  } 

  /* Pulls autos and configures the chooser */
  private void configureAutoChooser(Drivetrain drivetrain) {

    simpleAuto = new SimpleAuto(m_robotDrive);

    // Adds autos to the chooser
    m_chooser.addOption("simpleAuto", simpleAuto);

    // Puts autos on Shuffleboard
    Shuffleboard.getTab("RobotData").add("SelectAuto", m_chooser).withSize(2, 1).withPosition(0, 0);

    if (Configrun.get(false, "extraShuffleBoardToggle")) {

    }
  }

  /**
   * @return Selected Auto
   */
  public Command getAuto() {

    return m_chooser.getSelected();
  }

}