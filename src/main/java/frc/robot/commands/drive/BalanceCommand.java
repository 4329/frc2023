package frc.robot.commands.drive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utilities.HoorayConfig;
import frc.robot.Constants;
import frc.robot.subsystems.swerve.Drivetrain;

public class BalanceCommand extends CommandBase{

    Drivetrain drivetrain;

    private PIDController balancePID;
    public GenericEntry atSetpoint;
    public GenericEntry dilfs;
    public GenericEntry commandRuningdkj;

    public BalanceCommand(Drivetrain drivetrain) {

        this.drivetrain = drivetrain;

        balancePID = new PIDController(0.03, 0, 0);
        balancePID.setTolerance(1);
        balancePID.setSetpoint(0);

        addRequirements(drivetrain);

        // dilfs = Shuffleboard.getTab("RobotData").add("pos error", 0).getEntry();
        // commandRuningdkj = Shuffleboard.getTab("RobotData").add("rjeao", false).getEntry();
    }

    @Override
    public void initialize() {
        commandRuningdkj.setBoolean(true);
    }
    
    @Override
    public void execute() {

        if (!balancePID.atSetpoint()) {
            
            drivetrain.drive(balancePID.calculate(drivetrain.getRoll()), 0, 0, true);
        }

        else {

            drivetrain.lock();
        }

        // atSetpoint.setBoolean(balancePID.atSetpoint());
        // dilfs.setDouble(balancePID.getPositionError());
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.unlock();
        // commandRuningdkj.setBoolean(false);
    }


}
