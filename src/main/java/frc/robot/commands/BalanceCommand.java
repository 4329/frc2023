package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Configrun;
import frc.robot.Constants;
import frc.robot.subsystems.swerve.Drivetrain;

public class BalanceCommand extends CommandBase{

    Drivetrain drivetrain;

    private double roll;
    private PIDController balancePID;
    public GenericEntry rollyes;
    public GenericEntry jfldsa;
    public GenericEntry[] klasd; 
    public GenericEntry das;
    public GenericEntry sd;
    public GenericEntry lkadsfj;
    public GenericEntry dsljf;

    public BalanceCommand(Drivetrain drivetrain) {

        this.drivetrain = drivetrain;

        balancePID = new PIDController(Constants.AutoConstants.kPXController, 0, 0);
        balancePID.setTolerance(1.0);

        rollyes = Shuffleboard.getTab("sdaff").add("fsaed", 0).getEntry();
        jfldsa = Shuffleboard.getTab("sdaff").add("asd", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        klasd = new GenericEntry[] {
            Shuffleboard.getTab("sdaff").add("update p", 0.03).getEntry(),
            Shuffleboard.getTab("sdaff").add("update i", 0).getEntry(),
            Shuffleboard.getTab("sdaff").add("update d", 0).getEntry()
        };
        das = Shuffleboard.getTab("sdaff").add("shuffleboard output", 0).getEntry();
        sd = Shuffleboard.getTab("sdaff").add("yes", 1).getEntry();
        lkadsfj = Shuffleboard.getTab("sdaff").add("atsetpoint", false).getEntry();
        dsljf = Shuffleboard.getTab("sdaff").add("jselkif", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
        
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }
    
    @Override
    public void execute() {

        if (jfldsa.getBoolean(false)) {

            balancePID = new PIDController(klasd[0].getDouble(0), klasd[1].getDouble(0), klasd[2].getDouble(0));
            balancePID.setTolerance(sd.getDouble(0));
            
            System.out.println(balancePID.getP() + "wefaujlkfjnsoidadjfwiajofjweoajfoijao;fjoiajifoj;waeoifjiwoae;fjoiasj;iofsaej");
        }

        rollyes.setDouble(drivetrain.getRoll());

        das.setDouble(balancePID.calculate(drivetrain.getRoll(), 0));
        dsljf.setDouble(balancePID.getPositionError());

        lkadsfj.setBoolean(balancePID.atSetpoint());

        if (!balancePID.atSetpoint()) {
            
            drivetrain.drive(balancePID.calculate(drivetrain.getRoll(), 0), 0, 0, false);
        }
        
        // roll = drivetrain.getRoll() / Constants.DriveConstants.maxRampRoll;
        
        // if (Math.abs(roll) <= Constants.DriveConstants.maxRampDeviation || Math.abs(roll) >= Constants.DriveConstants.maxRampRoll + 5) {
            
        //     drivetrain.lock();
        // } else {
            
        //     drivetrain.unlock();

        //     // drivetrain.drive(Configrun.get(1, "rollDirection") * roll * Constants.DriveConstants.maxRampSpeed, 0, 0, false);

        // }
    }


    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.unlock();
    }


}
