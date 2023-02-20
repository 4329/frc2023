package frc.robot;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ArmExtendToZeroCommand;
import frc.robot.commands.ArmRetractFullCommand;
import frc.robot.commands.ArmRotateCommand;
import frc.robot.commands.ArmUnrotateCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.ChangeFieldOrientCommand;
import frc.robot.commands.CoastCommand;
import frc.robot.commands.CommandGroups;
import frc.robot.commands.DriveByController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ExtendRetractCommand;
import frc.robot.commands.HighArmCommand;
import frc.robot.commands.LowArmCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MidArmCommand;
import frc.robot.commands.HighWristCommand;
import frc.robot.commands.SafeExtendCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LowWristCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.PinchCommand;
import frc.robot.commands.ReleaseCommand;
import frc.robot.commands.ResetOdometryCommand;
import frc.robot.commands.WristRotateDownCommand;
import frc.robot.commands.WristRotateUpCommand;
import frc.robot.commands.WristToPositionCommand;
import frc.robot.commands.WristZeroCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.HoorayConfig;
import frc.robot.utilities.MathUtils;

/* (including subsystems, commands, and button mappings) should be declared here
*/
public class RobotContainer {
  
  // The robot's subsystems
  private final Drivetrain m_robotDrive;
  private final WristSubsystem wristSubsystem;
  private final ArmRotationSubsystem armRotationSubsystem;
  private final ArmExtensionSubsystem armExtensionSubsystem;
  
  final SendableChooser<Command> m_chooser;
  
  // The driver's controllers
  private final CommandXboxController driverController;
  private final CommandXboxController operatorController;
  private final DriveByController m_drive;

  private final ExampleCommand exampleCommand;
  private final ResetOdometryCommand resetOdometryCommandForward;
  private final ResetOdometryCommand resetOdometryCommandBackward;
  private final ChangeFieldOrientCommand changeFieldOrientCommand;
  private final BalanceCommand balanceCommand;
  private final HighArmCommand highArmCommand;
  private final MidArmCommand midArmCommand;
  private final LowArmCommand lowArmCommand;
  private final ClawSubsystem clawSubsystem;
  private final IntakeCommand intakeCommand;
  private final OuttakeCommand outtakeCommand;
  private final PinchCommand pinchCommand;
  private final ReleaseCommand releaseCommand;
  private final ColorDetector colorDetector;
  private final ArmRotateCommand armRotateCommand;
  private final ArmUnrotateCommand armUnrotateCommand;
  private final ExtendRetractCommand extendRetractCommand;
  private Command simpleAuto;
  private final WristRotateUpCommand wristRotateUpCommand;
  private final WristRotateDownCommand wristRotateDownCommand;
  private final ArmRetractFullCommand armRetractFullCommand;
  private final ArmExtendToZeroCommand armExtendToZeroCommand;

  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   *
   * @param drivetrain
   */

  public RobotContainer(Drivetrain drivetrain) {

    // pid = Shuffleboard.getTab("yes").add("name", 0).withWidget(BuiltInWidgets.kGraph)
        // .withProperties(Map.of("Automatic bounds", false, "Upper bound", 20)).getEntry();
    m_robotDrive = drivetrain;
    colorDetector = new ColorDetector();

    initializeCamera();

    armRotationSubsystem = new ArmRotationSubsystem();
    operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);
    driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    m_drive = new DriveByController(m_robotDrive, driverController);
    wristSubsystem = new WristSubsystem();

    m_chooser = new SendableChooser<>();

    exampleCommand = new ExampleCommand();
    resetOdometryCommandForward = new ResetOdometryCommand(new Pose2d(new Translation2d(), new Rotation2d(Math.PI)),
        drivetrain);
    resetOdometryCommandBackward = new ResetOdometryCommand(new Pose2d(new Translation2d(), new Rotation2d(0.0)),
        drivetrain);
    changeFieldOrientCommand = new ChangeFieldOrientCommand(m_drive);
    balanceCommand = new BalanceCommand(drivetrain);
    highArmCommand = new HighArmCommand(armRotationSubsystem);
    midArmCommand = new MidArmCommand(armRotationSubsystem);
    lowArmCommand = new LowArmCommand(armRotationSubsystem);

    clawSubsystem = new ClawSubsystem(colorDetector);
    intakeCommand = new IntakeCommand(clawSubsystem, colorDetector);
    outtakeCommand = new OuttakeCommand(clawSubsystem, armRotationSubsystem, colorDetector);
    pinchCommand = new PinchCommand(clawSubsystem);
    releaseCommand = new ReleaseCommand(clawSubsystem);
    armExtensionSubsystem = new ArmExtensionSubsystem();
    extendRetractCommand = new ExtendRetractCommand(armExtensionSubsystem, operatorController);
    armRotateCommand = new ArmRotateCommand(armRotationSubsystem);
    armUnrotateCommand = new ArmUnrotateCommand(armRotationSubsystem);
    wristRotateUpCommand = new WristRotateUpCommand(wristSubsystem);
    wristRotateDownCommand = new WristRotateDownCommand(wristSubsystem);
    armRetractFullCommand = new ArmRetractFullCommand(armExtensionSubsystem);
    armExtendToZeroCommand = new ArmExtendToZeroCommand(armExtensionSubsystem);
    configureButtonBindings();  /**
                                * Configure the button bindings to commands using configureButtonBindings
                                * function
                                */
    configureAutoChooser(drivetrain);
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

    HttpCamera limelight = new HttpCamera("Limelight", HoorayConfig.gimmeConfig().getLimelighturl());
    System.out.println(HoorayConfig.gimmeConfig().getLimelighturl());
    CameraServer.startAutomaticCapture(limelight);

    Shuffleboard.getTab("RobotData").add("Limelight Camera", limelight).withPosition(2, 0).withSize(2, 2)
        .withWidget(BuiltInWidgets.kCameraStream);
  }

  /* Autonomous :D */
  private Map<String, Command> createEventMap() {
    Map<String, Command> eventMap = new HashMap<>();
    eventMap.put("intakeCommand", intakeCommand);
    eventMap.put("extendCommand", extendRetractCommand);
    return eventMap;
  }

  private SwerveAutoBuilder createAutoBuilder() {

    SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
        m_robotDrive::getPose, // Pose2d supplier
        m_robotDrive::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
        Constants.DriveConstants.kDriveKinematics, // SwerveDriveKinematics
        new PIDConstants(Constants.AutoConstants.kPXController, 0.0, 0.0), // PID constants to correct for translation
                                                                           // error (used to create the X and Y PID
                                                                           // controllers)
        new PIDConstants(Constants.AutoConstants.kPYController, 0.0, 0.0), // PID constants to correct for rotation
                                                                           // error (used to create the rotation
                                                                           // controller)
        m_robotDrive::setModuleStates, // Module states consumer used to output to the drive subsystem
        createEventMap(),
        false, // Should the path be automatically mirrored depending on alliance color.
               // Optional, defaults to true
        m_robotDrive, // The drive subsystem. Used to properly set the requirements of path following
                      // commands
        clawSubsystem,
        armExtensionSubsystem,
        armRotationSubsystem);

    return autoBuilder;
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

    driverController.start().whileTrue(wristRotateUpCommand);
    driverController.back().whileTrue(wristRotateDownCommand);

    driverController.a().onTrue(CommandGroups.portalSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem, colorDetector));
    driverController.b().onTrue(CommandGroups.floorSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem, colorDetector));
    driverController.x().onTrue(exampleCommand);
    driverController.y().whileTrue(exampleCommand);

    driverController.povUp().onTrue(resetOdometryCommandForward);
    driverController.povDown().onTrue(resetOdometryCommandBackward);

    // Operator Controller
    operatorController.rightTrigger().whileTrue(extendRetractCommand);
    operatorController.leftTrigger().whileTrue(extendRetractCommand);

    operatorController.leftBumper().whileTrue(pinchCommand);
    operatorController.rightBumper().whileTrue(releaseCommand);

    operatorController.start().whileTrue(intakeCommand);
    operatorController.back().whileTrue(outtakeCommand);

    operatorController.a().onTrue(CommandGroups.lowScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));
    operatorController.b().onTrue(CommandGroups.midScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));
    operatorController.x().onTrue(CommandGroups.totalZero(armExtensionSubsystem, armRotationSubsystem, wristSubsystem));
    operatorController.y().onTrue(CommandGroups.highScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));

    operatorController.povUp().whileTrue(armRotateCommand);
    operatorController.povDown().whileTrue(armUnrotateCommand);
  }

  // jonathan was here today 2/3/2023
  /* Pulls autos and configures the chooser */
  SwerveAutoBuilder swerveAutoBuilder;

  private void configureAutoChooser(Drivetrain drivetrain) {

    swerveAutoBuilder = createAutoBuilder();
    File pathPlannerDirectory = new File(Filesystem.getDeployDirectory(), "pathplanner");
    for (File pathFile : pathPlannerDirectory.listFiles()) {

      System.out.println(pathFile);

      if (pathFile.isFile() && pathFile.getName().endsWith(".path")) {

        String name = pathFile.getName().replace(".path", "");
        PathPlannerTrajectory trajectory = PathPlanner.loadPath(name,
          new PathConstraints(Constants.AutoConstants.kMaxSpeed, Constants.AutoConstants.kMaxAcceleration));
        m_chooser.addOption(name, swerveAutoBuilder.fullAuto(trajectory));
      }
    }
    Shuffleboard.getTab("RobotData").add("SelectAuto", m_chooser).withSize(2, 1).withPosition(0, 0);
  }

  public void autonomousInit() {

    m_robotDrive.setDefaultCommand(m_drive);
  }

  public void teleopInit() {

    m_robotDrive.setDefaultCommand(m_drive);
    armExtensionSubsystem.resetSetpoint();
    armRotationSubsystem.resetSetpoint();
    wristSubsystem.resetSetpoint();
  }

  public void autonomousPeriodic() {

  }

  public void teleopPeriodic() {

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