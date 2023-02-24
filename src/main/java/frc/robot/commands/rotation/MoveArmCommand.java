package frc.robot.commands.rotation;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem.ArmHeight;

public abstract class MoveArmCommand extends CommandBase {

    private ArmRotationSubsystem armSubsystem;
    private ArmHeight armHeight;

    public MoveArmCommand(ArmRotationSubsystem armSubsystem, ArmHeight armHeight) {

        this.armSubsystem = armSubsystem;
        this.armHeight = armHeight;

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {

        armSubsystem.setArmPosition(armHeight);
    }

     @Override 
     public boolean isFinished() {
        
       return armSubsystem.armAtSetpoint();
    }
     
}
