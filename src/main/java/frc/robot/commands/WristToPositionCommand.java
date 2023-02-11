package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.WristSubsystem;

public class WristToPositionCommand extends InstantCommand {

    WristSubsystem wristSubsystem;
    double setpoint;

    public WristToPositionCommand(WristSubsystem wristSubsystem, double setpoint) {

        this.wristSubsystem = wristSubsystem;
        this.setpoint = setpoint;
    }

    @Override
    public void execute() {

        wristSubsystem.setWristPosition(setpoint);
    }

}