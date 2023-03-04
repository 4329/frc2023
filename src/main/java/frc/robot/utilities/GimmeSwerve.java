package frc.robot.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.swerve.Drivetrain;

public class GimmeSwerve extends SwerveAutoBuilder {

    private Drivetrain drivetrain;

    public GimmeSwerve(Supplier<Pose2d> poseSupplier, Consumer<Pose2d> resetPose, SwerveDriveKinematics kdrivekinematics, PIDConstants pidConstants, PIDConstants pidConstants2, Consumer<SwerveModuleState[]> outputModuleStates, Map<String, Command> createEventMap, boolean b, Drivetrain m_robotDrive) {

        super(poseSupplier, resetPose, kdrivekinematics, pidConstants, pidConstants2, outputModuleStates, createEventMap, b, m_robotDrive);
        this.drivetrain = m_robotDrive;
    }

    /**
   * Create a complete autonomous command group. This will reset the robot pose at the begininng of
   * the first path, follow paths, trigger events during path following, and run commands between
   * paths with stop events.
   *
   * <p>Using this does have its limitations, but it should be good enough for most teams. However,
   * if you want the auto command to function in a different way, you can create your own class that
   * extends BaseAutoBuilder and override existing builder methods to create the command group
   * however you wish.
   *
   * @param pathGroup Path group to follow during the auto
   * @return Autonomous command
   */
  public CommandBase fullAuto(List<PathPlannerTrajectory> pathGroup) {
    List<CommandBase> commands = new ArrayList<>();

    commands.add(resetPose(pathGroup.get(0)));

    for (PathPlannerTrajectory traj : pathGroup) {
      commands.add(stopEventGroup(traj.getStartStopEvent()));
      commands.add(followPathWithEvents(traj));
      commands.add(new InstantCommand(() -> drivetrain.stop()));
    }

    commands.add(stopEventGroup(pathGroup.get(pathGroup.size() - 1).getEndStopEvent()));

    return Commands.sequence(commands.toArray(CommandBase[]::new));
  }
    
}
