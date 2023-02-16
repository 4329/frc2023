package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;

public abstract class WristToPositionCommand extends CommandBase {

    WristSubsystem wristSubsystem;
    double setpoint;

    public WristToPositionCommand(WristSubsystem wristSubsystem, double setpoint) {

        this.wristSubsystem = wristSubsystem;
        this.setpoint = setpoint;
    }

    @Override
    public void execute() {

        wristSubsystem.setWristPosition(setpoint);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

    }

    @Override 
    public boolean isFinished() {

       return wristSubsystem.wristAtSetpoint();
    }
}