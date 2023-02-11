package frc.robot.commands;

import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ResetableSubsystem;

public class ResetToZeroCommand extends CommandBase {
    private List<ResetableSubsystem> resetableSubsystem = null;

    public ResetToZeroCommand(ResetableSubsystem... resetableSubsystem) {
        this.resetableSubsystem = Arrays.asList(resetableSubsystem);

        for (ResetableSubsystem rs : resetableSubsystem)

        {

            addRequirements((SubsystemBase) rs);

        }
    }

}
