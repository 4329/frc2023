package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ArmExtensionCommand;
import frc.robot.commands.ArmRotateCommand;
import frc.robot.commands.ArmUnrotateCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.ChangeFieldOrientCommand;
import frc.robot.commands.CoastCommand;
import frc.robot.commands.DriveByController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ExtendRetractCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.PinchCommand;
import frc.robot.commands.ReleaseCommand;
import frc.robot.commands.ResetOdometryCommand;
import frc.robot.commands.autos.SimpleAuto;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.swerve.Drivetrain;

/* (including subsystems, commands, and button mappings) should be declared here
*/
public class RobotContainer {


  // private final PneumaticHub pneumaticHub;

  // The robot's subsystems
  private final Drivetrain m_robotDrive;
  // private final TrackingTurretSubsystem trackingTurretSubsystem;
  // The driver's controllers

  final SendableChooser<Command> m_chooser;

  private final DriveByController m_drive;
  private final ExampleCommand exampleCommand;
  private final ResetOdometryCommand resetOdometryCommandForward;
  private final ResetOdometryCommand resetOdometryCommandBackward;
  private final ChangeFieldOrientCommand changeFieldOrientCommand;
  private final BalanceCommand balanceCommand;
  private final MoveArmCommand armToFive;
  private final MoveArmCommand armToZero;
  private final ArmRotationSubsystem armSubsystem;
  private final ArmExtensionSubsystem armExtensionSubsystem;
  private final ClawSubsystem clawSubsystem;
  private final IntakeCommand intakeCommand;
  private final OuttakeCommand outtakeCommand;
  private final PinchCommand pinchCommand;
  private final ReleaseCommand releaseCommand;
  private final ColorDetector colorDetector;
  private final ArmRotateCommand armRotateCommand;
  private final ArmUnrotateCommand armUnrotateCommand;
  private Command simpleAuto;
  private final ExtendRetractCommand extendRetractCommand;
  private final CommandXboxController driverController;
  private final CommandXboxController operatorController;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   *
   * @param drivetrain
   */

  public RobotContainer(Drivetrain drivetrain) {

    m_robotDrive = drivetrain;
    colorDetector = new ColorDetector();

    initializeCamera();

    armSubsystem = new ArmRotationSubsystem();
    operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);
    driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    m_drive = new DriveByController(m_robotDrive, driverController);

    m_chooser = new SendableChooser<>();
    configureAutoChooser(drivetrain);


    exampleCommand = new ExampleCommand();
    resetOdometryCommandForward = new ResetOdometryCommand(new Pose2d(new Translation2d(), new Rotation2d(Math.PI)),
        drivetrain);
    resetOdometryCommandBackward = new ResetOdometryCommand(new Pose2d(new Translation2d(), new Rotation2d(0.0)),
        drivetrain);
    changeFieldOrientCommand = new ChangeFieldOrientCommand(m_drive);
    balanceCommand = new BalanceCommand(drivetrain);
    armToFive = new MoveArmCommand(armSubsystem, -5);
    armToZero = new MoveArmCommand(armSubsystem, 0);

    clawSubsystem = new ClawSubsystem(colorDetector);
    intakeCommand = new IntakeCommand(clawSubsystem, colorDetector);
    outtakeCommand = new OuttakeCommand(clawSubsystem);
    pinchCommand = new PinchCommand(clawSubsystem);
    releaseCommand = new ReleaseCommand(clawSubsystem);
    armExtensionSubsystem = new ArmExtensionSubsystem();
    extendRetractCommand = new ExtendRetractCommand(armExtensionSubsystem, operatorController);
    armRotateCommand = new ArmRotateCommand(armSubsystem);
    armUnrotateCommand = new ArmUnrotateCommand(armSubsystem);
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
    driverController.rightTrigger().whileTrue(exampleCommand);
    driverController.leftTrigger().whileTrue(exampleCommand);

    driverController.leftBumper().onTrue(exampleCommand);
    driverController.rightBumper().onTrue(changeFieldOrientCommand);

    driverController.start().whileTrue(exampleCommand);
    driverController.back().whileTrue(exampleCommand);
    
    driverController.a().whileTrue(exampleCommand);
    driverController.b().whileTrue(exampleCommand);
    driverController.x().whileTrue(exampleCommand);
    driverController.y().whileTrue(exampleCommand);
    
    driverController.povUp().onTrue(resetOdometryCommandForward);
    driverController.povDown().onTrue(resetOdometryCommandBackward);
    

    // Operator Controller
    operatorController.rightTrigger().whileTrue(extendRetractCommand);
    operatorController.leftTrigger().whileTrue(extendRetractCommand);

    operatorController.leftBumper().whileTrue(pinchCommand);
    operatorController.rightBumper().whileTrue(releaseCommand);

    operatorController.start().whileTrue(new ArmExtensionCommand(armExtensionSubsystem, 10));
    operatorController.back().whileTrue(new ArmExtensionCommand(armExtensionSubsystem, 0));
    
    operatorController.a().onTrue(armToFive);
    operatorController.b().onTrue(armToZero);
    operatorController.x().whileTrue(intakeCommand);
    operatorController.y().whileTrue(outtakeCommand);

    operatorController.povUp().onTrue(armRotateCommand);
    operatorController.povDown().onTrue(armUnrotateCommand);
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



public void autonomousInit() {

  m_robotDrive.setDefaultCommand(m_drive);
}

public void teleopInit() {

  m_robotDrive.setDefaultCommand(m_drive);
}

  /**
   * @return Selected Auto
   */   
  public Command getAuto() {

    return m_chooser.getSelected();
  }

  public void configureTestMode() {

    m_robotDrive.setDefaultCommand(new CoastCommand(m_robotDrive));
  }

}