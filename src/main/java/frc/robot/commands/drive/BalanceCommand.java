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
import frc.robot.utilities.HoorayConfig;

public class BalanceCommand extends CommandBase{

    Drivetrain drivetrain;
    BalanceSubsystem balanceSubsystem;

    private double roll;

    public GenericEntry atSetpoint;
    public GenericEntry dilfs;
    public GenericEntry commandRuningdkj;
    private double pidCalc;
    private double ff = 0;
    private PIDController balancePID;
   



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

        balancePID = new PIDController(balanceSubsystem.yay[0].getDouble(0.015), balanceSubsystem.yay[1].getDouble(0.01), balanceSubsystem.yay[2].getDouble(0));
        balancePID.setTolerance(balanceSubsystem.yay[3].getDouble(1));
        // ff = balanceSubsystem.yay[4].getDouble(0);
        balancePID.setSetpoint(0);
        balancePID.setIntegratorRange(-balanceSubsystem.irange.getDouble(0.1), balanceSubsystem.irange.getDouble(0.1));
    }

    @Override
    public void execute() {

        double output;
        
        //  ffCalc = ff.calculate(0);
        pidCalc = balancePID.calculate(drivetrain.getOffsetRoll());
        balanceSubsystem.out.setDouble(pidCalc);
        
        System.out.println("output is: " + pidCalc);
        if (!balancePID.atSetpoint()) {
            
            balanceSubsystem.atSetpoint.setBoolean(false);
            drivetrain.unlock();
            drivetrain.drive(pidCalc, 0, 0, false);
        } else {
            
            drivetrain.lock();
            balanceSubsystem.atSetpoint.setBoolean(true);
        }

        // balanceSubsystem.happiness.setDouble(balancePID.getPositionError());



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
        drivetrain.stop();
        // commandRuningdkj.setBoolean(false);
    }


}