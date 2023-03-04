package frc.robot.commands.drive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utilities.HoorayConfig;
import frc.robot.Constants;
import frc.robot.subsystems.BalanceSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;

public class BalanceCommand extends CommandBase{

    Drivetrain drivetrain;
    BalanceSubsystem balanceSubsystem;

    private PIDController balancePID;

    public GenericEntry atSetpoint;
    public GenericEntry dilfs;
    public GenericEntry commandRuningdkj;
    private double pidCalc;
    private double ff = 0.1;



    public BalanceCommand(Drivetrain drivetrain, BalanceSubsystem balanceSubsystem) {

        this.drivetrain = drivetrain;
        this.balanceSubsystem = balanceSubsystem;

        balancePID = new PIDController(0.01, 0, 0);
       // ff = new SimpleMotorFeedforward(0.05, 0.0005);
        
        
        balancePID.setTolerance(1);
        balancePID.setSetpoint(0);

        addRequirements(drivetrain);



        // dilfs = Shuffleboard.getTab("RobotData").add("pos error", 0).getEntry();
        // commandRuningdkj = Shuffleboard.getTab("RobotData").add("rjeao", false).getEntry();
    }

    @Override
    public void initialize() {
        //commandRuningdkj.setBoolean(true);

        balancePID = new PIDController(balanceSubsystem.yay[0].getDouble(0), balanceSubsystem.yay[1].getDouble(0), balanceSubsystem.yay[2].getDouble(0));
        balancePID.setTolerance(balanceSubsystem.yay[3].getDouble(0));
        ff = balanceSubsystem.yay[4].getDouble(0);
        balancePID.setSetpoint(0);
    }
    
    @Override
    public void execute() {

        double output;

      //  ffCalc = ff.calculate(0);
        pidCalc = balancePID.calculate(drivetrain.getOffsetRoll());

        if (pidCalc < 0) {

            output = pidCalc - ff;            
        } else {

            output = pidCalc + ff;
        }
        System.out.println("output is: " + output);
        if (!balancePID.atSetpoint()) {
            
            drivetrain.unlock();
            drivetrain.drive(output, 0, 0, true);
        }

        else {

            drivetrain.lock();
        }

        balanceSubsystem.happiness.setDouble(balancePID.getPositionError());



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
