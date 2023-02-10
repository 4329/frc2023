package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ZeroTestingSubsystem;

public class BopItTestItCommand extends CommandBase {

    private ZeroTestingSubsystem   zeroTestingSubsystem;

    public BopItTestItCommand(ZeroTestingSubsystem zeroTestingSubsystem) {
        
        this.zeroTestingSubsystem = zeroTestingSubsystem;
    }

    @Override
    public void execute() {

        zeroTestingSubsystem.BopItSpinIt();
    }

    @Override
    public void end(boolean interrupted) {

        zeroTestingSubsystem.YouLose();
    }

}