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

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.controller.PIDController;
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
import frc.robot.commands.CommandGroups;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SwerveAlignZeroCommand;
import frc.robot.commands.claw.IntakeCommand;
import frc.robot.commands.claw.ManualHighShotCommand;
import frc.robot.commands.claw.ManualMidShotCommand;
import frc.robot.commands.claw.OuttakeCommand;
import frc.robot.commands.claw.TogglePinchCommand;
import frc.robot.commands.claw.ReleaseCommand;
import frc.robot.commands.claw.ToggleIntakeCommand;
import frc.robot.commands.drive.BalanceCommand;
import frc.robot.commands.drive.ChangeFieldOrientCommand;
import frc.robot.commands.drive.CoastCommand;
import frc.robot.commands.drive.DriveByController;
import frc.robot.commands.drive.ResetOdometryCommand;
import frc.robot.commands.extend.ArmExtendToZeroCommand;
import frc.robot.commands.extend.ArmRetractFullCommand;
import frc.robot.commands.extend.ExtendRetractCommand;
import frc.robot.commands.rotation.ArmRotateCommand;
import frc.robot.commands.rotation.ArmUnrotateCommand;
import frc.robot.commands.rotation.HighArmCommand;
import frc.robot.commands.rotation.LowArmCommand;
import frc.robot.commands.rotation.MidArmCommand;
import frc.robot.commands.wrist.WristRotateDownCommand;
import frc.robot.commands.wrist.WristRotateUpCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.ManualColorDetector;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.HoorayConfig;
import frc.robot.commands.claw.ToggleElementCommand;

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
  private final TogglePinchCommand togglePinchCommand;
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
  private final ToggleElementCommand toggleElementCommand;
  private final ToggleIntakeCommand toggleIntakeCommand;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   *
   * @param drivetrain
   */

  public RobotContainer(Drivetrain drivetrain) {

    // pid = Shuffleboard.getTab("yes").add("name", 0).withWidget(BuiltInWidgets.kGraph)
        // .withProperties(Map.of("Automatic bounds", false, "Upper bound", 20)).getEntry();
    m_robotDrive = drivetrain;
    colorDetector = new ManualColorDetector();

    initializeCamera();

    armExtensionSubsystem = new ArmExtensionSubsystem();
    armRotationSubsystem = new ArmRotationSubsystem();
    operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);
    driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    m_drive = new DriveByController(m_robotDrive, driverController);
    wristSubsystem = new WristSubsystem();
    clawSubsystem = new ClawSubsystem(colorDetector);

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

    intakeCommand = new IntakeCommand(clawSubsystem, colorDetector);
    outtakeCommand = new OuttakeCommand(clawSubsystem, armRotationSubsystem, colorDetector);
    togglePinchCommand = new TogglePinchCommand(clawSubsystem);
    releaseCommand = new ReleaseCommand(clawSubsystem);
    extendRetractCommand = new ExtendRetractCommand(armExtensionSubsystem, driverController);
    armRotateCommand = new ArmRotateCommand(armRotationSubsystem);
    armUnrotateCommand = new ArmUnrotateCommand(armRotationSubsystem);
    wristRotateUpCommand = new WristRotateUpCommand(wristSubsystem);
    wristRotateDownCommand = new WristRotateDownCommand(wristSubsystem);
    armRetractFullCommand = new ArmRetractFullCommand(armExtensionSubsystem);
    armExtendToZeroCommand = new ArmExtendToZeroCommand(armExtensionSubsystem);
    toggleElementCommand = new ToggleElementCommand((ManualColorDetector) colorDetector);
    toggleIntakeCommand = new ToggleIntakeCommand(clawSubsystem);
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
    eventMap.put("outtake", outtakeCommand);
    eventMap.put("zero", CommandGroups.totalZero(armExtensionSubsystem, armRotationSubsystem, wristSubsystem, clawSubsystem, colorDetector));
    //eventMap.put("highPos", CommandGroups.highScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));
   // eventMap.put("flootCommand", CommandGroups.floorSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem, colorDetector));
    //eventMap.put("highPos", exampleCommand);
    eventMap.put("zero", exampleCommand);
    eventMap.put("outtake", exampleCommand);


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
        new PIDConstants(Constants.AutoConstants.kPThetaController, 0.0, 0.0), // PID constants to correct for rotation
        //                                                                    // error (used to create the rotation

        
        m_robotDrive::setModuleStates, // Module states consumer used to output to the drive subsystem
        createEventMap(),
        true, // Should the path be automatically mirrored depending on alliance color.
               // Optional, defaults to true
        m_robotDrive // The drive subsystem. Used to properly set the requirements of path following
                      // commands
);

    return autoBuilder;
  }

  /**
   * 
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of
   * its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or
   * {@link XboxController}), and then calling passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {

    // Driver Controller
    driverController.rightTrigger().whileTrue(new ManualHighShotCommand(clawSubsystem, driverController, colorDetector)); //arm extend
    driverController.leftTrigger().whileTrue(new ManualMidShotCommand(clawSubsystem, driverController, colorDetector)); //arm retract
    
    driverController.rightBumper().whileTrue(armRotateCommand); //arm up
    driverController.leftBumper().whileTrue(armUnrotateCommand); //arm down

    driverController.start().whileTrue(exampleCommand); //to april tag or conecubetoggle
    driverController.back().onTrue(changeFieldOrientCommand);

    driverController.a().onTrue(toggleIntakeCommand);
    driverController.b().onTrue(togglePinchCommand);
    driverController.x().whileTrue(toggleElementCommand);
    driverController.y().onTrue(CommandGroups.highScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));

    driverController.povUp().onTrue(CommandGroups.portalSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem)); //intake for substation
    driverController.povRight().onTrue(CommandGroups.midScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));
    driverController.povLeft().onTrue(CommandGroups.totalZero(armExtensionSubsystem, armRotationSubsystem, wristSubsystem, clawSubsystem, colorDetector));
    driverController.povDown().onTrue(CommandGroups.floorSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem, colorDetector));

    driverController.rightStick().whileTrue(balanceCommand);
    driverController.leftStick().whileTrue(resetOdometryCommandForward); //field orient
    
    // Operator Controller
    operatorController.rightTrigger().whileTrue(new ManualHighShotCommand(clawSubsystem, driverController, colorDetector)); //arm extend
    operatorController.leftTrigger().whileTrue(new ManualMidShotCommand(clawSubsystem, driverController, colorDetector)); //arm retract
  
    operatorController.rightBumper().whileTrue(armRotateCommand); //arm up
    operatorController.leftBumper().whileTrue(armUnrotateCommand); //arm down

    operatorController.start().whileTrue(exampleCommand); //to april tag or conecubetoggle
    operatorController.back().onTrue(changeFieldOrientCommand);

    operatorController.a().onTrue(toggleIntakeCommand);
    operatorController.b().onTrue(togglePinchCommand);
    operatorController.x().whileTrue(toggleElementCommand);
    operatorController.y().onTrue(CommandGroups.highScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));

    operatorController.povUp().onTrue(CommandGroups.portalSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem)); //intake for substation
    operatorController.povRight().onTrue(CommandGroups.midScore(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem));
    operatorController.povLeft().onTrue(CommandGroups.totalZero(armExtensionSubsystem, armRotationSubsystem, wristSubsystem, clawSubsystem, colorDetector));
    operatorController.povDown().onTrue(CommandGroups.floorSnag(armExtensionSubsystem, armRotationSubsystem, clawSubsystem, wristSubsystem, colorDetector));
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
        List<PathPlannerTrajectory> trajectories = PathPlanner.loadPathGroup(name,
          new PathConstraints(Constants.AutoConstants.kMaxSpeed, Constants.AutoConstants.kMaxAcceleration));
        Command pathCommand =  swerveAutoBuilder.fullAuto(trajectories);
        if (name.endsWith("BalanceAuto")) {

          m_chooser.addOption(name, new SequentialCommandGroup(pathCommand, new BalanceCommand(m_robotDrive).withTimeout(5)));
        } else {

          m_chooser.addOption(name, pathCommand);
        }
      }
    }
    Shuffleboard.getTab("RobotData").add("SelectAuto", m_chooser).withSize(2, 1).withPosition(0, 0);
  }

  public void autonomousInit() {

//m_robotDrive.setDefaultCommand(m_drive);
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