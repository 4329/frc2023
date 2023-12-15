package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.LimlighSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.utilities.MathUtils;

public class CenterOnTargetCommand extends CommandBase {

    private final LimlighSubsystem limlighSubsystem;
    private final Drivetrain drivetrain;
    private final double targetId;
    private final PIDController centerPID;
    private final PIDController forwardPID;
    private final PIDController rotationPID;
    private CommandXboxController xboxController;
    private Timer timer = new Timer();
    //GenericEntry swervepwooo = Shuffleboard.getTab("ikfsdal").add("swervepwooo", 0.000005).getEntry();
    //GenericEntry swervetwooo = Shuffleboard.getTab("ikfsdal").add("johnwuzhere", 0).getEntry();
    GenericEntry aprilTagOrLimLigh;
    GenericEntry pointset;


    private boolean usingAprilTag;

    public CenterOnTargetCommand(LimlighSubsystem limlighSubsystem, Drivetrain m_drivetrain, double targetId, CommandXboxController xboxController) {

        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        this.targetId = targetId;
        this.xboxController = xboxController;
        centerPID = new PIDController(0, 0, 0);
        forwardPID = new PIDController(0, 0, 0);
        rotationPID = new PIDController(0.00002, 0, 0);
        addRequirements(limlighSubsystem, m_drivetrain);
        //aprilTagOrLimLigh = Shuffleboard.getTab("ikfsdal").add("painhahah", false).getEntry();
        //pointset = Shuffleboard.getTab("ikfsdal").add("at", false).getEntry();
    }

    @Override

    public void initialize() {

        timer.reset();
        timer.start();

        if (limlighSubsystem.getPipeline() == 0) {

            usingAprilTag = true;

            centerPID.setP(0.075);

            centerPID.setTolerance(7);

            centerPID.setSetpoint(-4);

            forwardPID.setP(1.5);

            forwardPID.setTolerance(0.05);

            forwardPID.setSetpoint(6.3);

            rotationPID.setP(0.03);

            rotationPID.setTolerance(1.5);

            rotationPID.setSetpoint(5);

        } else {

            usingAprilTag = false;

            centerPID.setP(0);

            centerPID.setTolerance(10000);

            centerPID.setSetpoint(0);

            forwardPID.setP(0);

            forwardPID.setTolerance(10000);

            forwardPID.setSetpoint(0);


            //rotationPID.setP(swervepwooo.getDouble(0));

            //rotationPID.setTolerance(swervetwooo.getDouble(0));

            rotationPID.setSetpoint(0);
        }


    }

    @Override

    public void execute() {

        double centerCalc = 0;
        double forwardCalc = 0;
        double rotationCalc = 0;
        if ((limlighSubsystem.getTargetId() == targetId && usingAprilTag)) {

            centerCalc = centerPID.calculate(limlighSubsystem.getTargetx());

            forwardCalc = forwardPID.calculate(limlighSubsystem.getCalculatedPoseZ());

            rotationCalc = rotationPID.calculate(limlighSubsystem.getCalculatedPoseRot());

            aprilTagOrLimLigh.setBoolean(true);

        } else if (limlighSubsystem.targetVisible()) {

            aprilTagOrLimLigh.setBoolean(false);

            rotationCalc = rotationPID.calculate(limlighSubsystem.getTargetx(), 0);
            forwardCalc = forwardPID.calculate(0);
            centerCalc = centerPID.calculate(0);

            if (rotationCalc > Constants.DriveConstants.kMaxAngularSpeed) {

                rotationCalc = Constants.DriveConstants.kMaxAngularSpeed;
            } else if (rotationCalc < -Constants.DriveConstants.kMaxAngularSpeed) {

                rotationCalc = -Constants.DriveConstants.kMaxAngularSpeed;
            } else if (rotationPID.atSetpoint()){

                rotationCalc = 0;
            }
          

            double adjTranslation = ((Constants.DriveConstants.kMaxAngularSpeed - Math.abs(rotationCalc))
                / Constants.DriveConstants.kMaxAngularSpeed) * 0.5;

            drivetrain.drive(

                -inputTransform(xboxController.getLeftY()) * (Constants.DriveConstants.kMaxSpeedMetersPerSecond * adjTranslation),
                -inputTransform(xboxController.getLeftX()) * (Constants.DriveConstants.kMaxSpeedMetersPerSecond * adjTranslation),
                rotationCalc,
                true
            );
            
        }

        if ((!centerPID.atSetpoint() || !rotationPID.atSetpoint() || !forwardPID.atSetpoint())) {

            drivetrain.unlock();

            drivetrain.drive(forwardCalc, centerCalc, rotationCalc, false);

            pointset.setBoolean(false);

        } else {

            drivetrain.lock();

            pointset.setBoolean(true);

        }

    }

    @Override

    public boolean isFinished() {

        return false;

    }

    @Override

    public void end(boolean interrupted) {

        drivetrain.unlock();

        aprilTagOrLimLigh.setBoolean(false);

    }

    private double inputTransform(double input){

        return MathUtils.singedSquare(MathUtils.applyDeadband(input));
    }

}
