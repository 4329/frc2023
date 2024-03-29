// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.HoorayConfig;
import frc.robot.utilities.SwerveAlignment;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private SwerveAlignment m_swerveAlignment;
  private Drivetrain drivetrain;

  @Override
  public void robotInit() {
    
    HoorayConfig.gimmeConfig();
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    drivetrain = new Drivetrain();
    m_robotContainer = new RobotContainer(drivetrain);
    drivetrain.resetOdometry(new Pose2d());
  }

  @Override
  public void robotPeriodic() {

    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {

    drivetrain.brakeMode();
  }

  @Override
  public void disabledPeriodic() {

  }

  @Override
  public void autonomousInit() {
    m_robotContainer.autonomousInit();
    m_autonomousCommand = m_robotContainer.getAuto();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {

      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {

    m_robotContainer.autonomousPeriodic();
  }

  @Override
  public void autonomousExit() {

    Command resetForTeliOp = new InstantCommand(
        () -> drivetrain.resetOdometry(new Pose2d(new Translation2d(), new Rotation2d(0.0))));
    resetForTeliOp.schedule();
  }

  @Override
  public void teleopInit() {

    drivetrain.brakeMode();
    m_robotContainer.teleopInit();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {

    m_robotContainer.teleopPeriodic();
  }

  @Override
  public void testInit() {

    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    drivetrain.coastMode();
    m_robotContainer.configureTestMode();
    
    if (m_swerveAlignment == null) {
      // This prevents 2 sets of widgets from appearing when disabling & enabling the
      // robot, causing a crash
      m_swerveAlignment = new SwerveAlignment(drivetrain);
      m_swerveAlignment.initSwerveAlignmentWidgets();
    }
  }

  @Override
  public void testPeriodic() {

    m_swerveAlignment.updateSwerveAlignment();
  }
}