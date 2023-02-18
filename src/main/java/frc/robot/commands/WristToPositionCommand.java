package frc.robot.commands;

import org.apache.commons.io.output.WriterOutputStream;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.subsystems.WristSubsystem.WristAngle;

public abstract class WristToPositionCommand extends CommandBase {

    WristSubsystem wristSubsystem;
    WristAngle setpoint;

    public WristToPositionCommand(WristSubsystem wristSubsystem, WristAngle setpoint) {

        this.wristSubsystem = wristSubsystem;
        this.setpoint = setpoint;
    }

    @Override
    public void execute() {

        wristSubsystem.setWristPosition(setpoint);

    }

    @Override 
    public boolean isFinished() {

       return wristSubsystem.wristAtSetpoint();
    }
}