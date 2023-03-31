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
    GenericEntry tGEntry = Shuffleboard.getTab("ikfsdal").add("tG", 1).getEntry();
    GenericEntry tREntry = Shuffleboard.getTab("ikfsdal").add("tR", 1).getEntry();

    GenericEntry jfdsoujfsadoiijofdsa;

    GenericEntry pointset;
    private PIDController swervePidController = new PIDController(5, 0, 0.2);

    GenericEntry[] go;

    private boolean usingAprilTag;

    public CenterOnTargetCommand(LimlighSubsystem limlighSubsystem, Drivetrain m_drivetrain, double targetId, CommandXboxController xboxController) {

        this.limlighSubsystem = limlighSubsystem;
        this.drivetrain = m_drivetrain;
        this.targetId = targetId;
        this.xboxController = xboxController;
        centerPID = new PIDController(0, 0, 0);
        forwardPID = new PIDController(0, 0, 0);
        rotationPID = new PIDController(0, 0, 0);
        addRequirements(limlighSubsystem, m_drivetrain);
        jfdsoujfsadoiijofdsa = Shuffleboard.getTab("ikfsdal").add("painhahah", false).getEntry();
        pointset = Shuffleboard.getTab("ikfsdal").add("at", false).getEntry();
       


        go = new GenericEntry[] {

                Shuffleboard.getTab("ikfsdal").add("p1", 0).getEntry(),
                Shuffleboard.getTab("ikfsdal").add("t1", 0).getEntry(),
                Shuffleboard.getTab("ikfsdal").add("s1", 0).getEntry()

        };

        swervePidController.enableContinuousInput(0, 2 * Math.PI);

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

            centerPID.setTolerance(0);

            centerPID.setSetpoint(0);

            forwardPID.setP(0);

            forwardPID.setTolerance(0);

            forwardPID.setSetpoint(0);

            rotationPID.setP(go[0].getDouble(0));

            rotationPID.setTolerance(go[1].getDouble(0));

            rotationPID.setSetpoint(go[2].getDouble(0));

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

            jfdsoujfsadoiijofdsa.setBoolean(true);

        } else if (limlighSubsystem.targetVisible()) {

            jfdsoujfsadoiijofdsa.setBoolean(false);

            rotationCalc = swervePidController.calculate(limlighSubsystem.getTargetx(), 0);

            if (rotationCalc > Constants.DriveConstants.kMaxAngularSpeed) {

                rotationCalc = Constants.DriveConstants.kMaxAngularSpeed;
            } else if (rotationCalc < -Constants.DriveConstants.kMaxAngularSpeed) {

                rotationCalc = -Constants.DriveConstants.kMaxAngularSpeed;
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

        jfdsoujfsadoiijofdsa.setBoolean(false);

    }

    private double inputTransform(double input){

        return MathUtils.singedSquare(MathUtils.applyDeadband(input));
    }

}
