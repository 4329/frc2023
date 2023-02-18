package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ArmExtensionCommand;
import frc.robot.commands.ArmRotateCommand;
import frc.robot.commands.LowArmCommand;
import frc.robot.commands.MidArmCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.WristSubsystem;

public class CommandGroups {

    public static CommandBase highScore(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

            new InitialArmCommand(armRotationSubsystem),
            new WristZeroCommand(wristSubsystem),
            new HighArmCommand(armRotationSubsystem),
            new ArmExtensionCommand(armExtensionSubsystem, 166.8),
            new OuttakeCommand(clawSubsystem)
        );
    }

    public static CommandBase midScore(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

        //     new InitialArmCommand(armRotationSubsystem),
        //     new WristZeroCommand(wristSubsystem),
        //     new MidArmCommand(armRotationSubsystem),
        //     new ArmExtensionCommand(armExtensionSubsystem, 161),
        //     new OuttakeCommand(clawSubsystem)
        );
    }

    public static CommandBase lowScore(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

        //     new InitialArmCommand(armRotationSubsystem),
        //     new HighWristCommand(wristSubsystem),
        //     new ReleaseCommand(clawSubsystem),
        //     new ArmExtensionCommand(armExtensionSubsystem, 40)
        );
    }

    public static CommandBase portalSnagCone(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem, ColorDetector colorDetector) {

        return new SequentialCommandGroup(

            new PortalArmCommand(armRotationSubsystem),
            new PortalWristCommand(wristSubsystem),
            new PinchCommand(clawSubsystem),
            new IntakeCommand(clawSubsystem, colorDetector)
        );
    }

    public static CommandBase portalSnagCube(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem, ColorDetector colorDetector) {

        return new SequentialCommandGroup(

            new PortalArmCommand(armRotationSubsystem),
            new PortalWristCommand(wristSubsystem),
            new ReleaseCommand(clawSubsystem),
            new IntakeCommand(clawSubsystem, colorDetector)
        );
    }

}
