package frc.robot.commands;

import org.junit.experimental.ParallelComputer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
            new ArmExtendFullCommand(armExtensionSubsystem),
            
            new ParallelCommandGroup(                
                new HighWristCommand(wristSubsystem),
                new HighArmCommand(armRotationSubsystem)
            )   
        );
    }

    public static CommandBase midScore(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

            new InitialArmCommand(armRotationSubsystem),
            new ArmExtendFullCommand(armExtensionSubsystem),

            new ParallelCommandGroup(
                new MidWristCommand(wristSubsystem),
                new MidArmCommand(armRotationSubsystem)
            )
        );
    }

    public static CommandBase lowScore(ArmExtensionSubsystem armExtensionSubsystem, ArmRotationSubsystem armRotationSubsystem, ClawSubsystem clawSubsystem, WristSubsystem wristSubsystem) {

        return new SequentialCommandGroup(

        new LowArmCommand(armRotationSubsystem),
        new LowWristCommand(wristSubsystem)
            
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
