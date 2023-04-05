package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.claw.ConeZeroCommand;
import frc.robot.commands.claw.ReleaseCommand;
import frc.robot.commands.drive.CenterOnRetroreflectiveCommand;
import frc.robot.commands.extend.ArmExtendFloorCommand;
import frc.robot.commands.extend.ArmExtendHighCommand;
import frc.robot.commands.extend.ArmExtendMidCommand;
import frc.robot.commands.extend.ArmExtendMidScoreCommand;
import frc.robot.commands.extend.ArmExtendStartCommand;
import frc.robot.commands.extend.ArmExtendToZeroCommand;
import frc.robot.commands.extend.ArmRetractFullCommand;
import frc.robot.commands.rotation.ArmRotateFloorCommand;
import frc.robot.commands.rotation.ArmRotateMidScoreCommand;
import frc.robot.commands.rotation.HighArmCommand;
import frc.robot.commands.rotation.LowArmCommand;
import frc.robot.commands.rotation.MidArmCommand;
import frc.robot.commands.rotation.PortalArmCommand;
import frc.robot.commands.rotation.SafeRotateCommand;
import frc.robot.commands.rotation.ZeroArmCommand;
import frc.robot.commands.wrist.HighWristCommand;
import frc.robot.commands.wrist.LowWristCommand;
import frc.robot.commands.wrist.MidWristCommand;
import frc.robot.commands.wrist.PortalWristCommand;
import frc.robot.commands.wrist.WristFloorCommand;
import frc.robot.commands.wrist.WristZeroCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetectorSubsystem;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;

public class CommandGroups {

    public static CommandBase highScore(ArmExtensionSubsystem armExtensionSubsystem,
            ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

                new ArmExtendStartCommand(armExtensionSubsystem),
                new SafeRotateCommand(armRotationSubsystem),
                new ArmExtendHighCommand(armExtensionSubsystem),
                new ParallelCommandGroup(

                        new HighWristCommand(wristSubsystem),
                        new HighArmCommand(armRotationSubsystem)));
    }

    public static CommandBase midScore(ArmExtensionSubsystem armExtensionSubsystem,
            ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

                new ArmExtendStartCommand(armExtensionSubsystem),
                new SafeRotateCommand(armRotationSubsystem),
                new ArmExtendMidCommand(armExtensionSubsystem),
                new ParallelCommandGroup(

                        new MidWristCommand(wristSubsystem),
                        new MidArmCommand(armRotationSubsystem)));
    }

    public static CommandBase lowScore(ArmExtensionSubsystem armExtensionSubsystem,
            ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

                new ArmExtendStartCommand(armExtensionSubsystem),
                new LowArmCommand(armRotationSubsystem),
                new LowWristCommand(wristSubsystem)

        );
    }

    public static CommandBase portalSnag(ArmExtensionSubsystem armExtensionSubsystem,
            ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

                new ArmRetractFullCommand(armExtensionSubsystem),
                new ParallelCommandGroup(

                        new PortalArmCommand(armRotationSubsystem),
                        new SequentialCommandGroup(

                                new WaitCommand(0.125),
                                new PortalWristCommand(wristSubsystem))));
    }

    public static CommandBase floorSnag(ArmExtensionSubsystem armExtensionSubsystem,
            ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem,
            ColorDetectorSubsystem colorDetector) {

        return new SequentialCommandGroup(

                new ArmExtendStartCommand(armExtensionSubsystem),
                new ArmRotateFloorCommand(armRotationSubsystem),
                new ParallelCommandGroup(

                        new ArmExtendFloorCommand(armExtensionSubsystem),
                        new WristFloorCommand(wristSubsystem)));
    }

    public static CommandBase totalZero(ArmExtensionSubsystem armExtensionSubsystem,
            ArmRotationSubsystem armRotationSubsystem, WristSubsystem wristSubsystem, ClawSubsystem clawSubsystem,
            ColorDetectorSubsystem colorDetector) {

        return new SequentialCommandGroup(

                new ParallelCommandGroup(

                        new ArmRetractFullCommand(armExtensionSubsystem),
                        new WristZeroCommand(wristSubsystem)),
                new ZeroArmCommand(armRotationSubsystem),
                new ArmExtendToZeroCommand(armExtensionSubsystem),
                new ConeZeroCommand(clawSubsystem, colorDetector)

        // new IntakeCommand(clawSubsystem, colorDetector).withTimeout(10)
        );
    }

    public static CommandBase autoDroptomousPrime(ArmExtensionSubsystem armExtensionSubsystem, ClawSubsystem clawSubsystem,
            LimlighSubsystem limlighSubsystem, Drivetrain drivetrain, double targetId,
            CommandXboxController xboxController, ArmRotationSubsystem armRotationSubsystem) {

        return new SequentialCommandGroup(
                new ParallelCommandGroup(

                        new ArmExtendMidScoreCommand(armExtensionSubsystem),
                        new ArmRotateMidScoreCommand(armRotationSubsystem)
                ),
                new CenterOnRetroreflectiveCommand(limlighSubsystem, drivetrain, xboxController),
                new WaitCommand(0.3),
                new ReleaseCommand(clawSubsystem),
                new ArmRetractFullCommand(armExtensionSubsystem));
    }

}