package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ColorDetector;
import frc.robot.subsystems.CoopertitionLEDs;
import frc.robot.subsystems.ColorDetector.FieldElement;

public class LedDefaultCommand extends CommandBase{

    private CoopertitionLEDs coopertitionLEDs;
    private ColorDetector colorDetector;
    public LedDefaultCommand(CoopertitionLEDs coopertitionLEDs, ColorDetector colorDetector) {
    
       this.colorDetector = colorDetector;
       this.coopertitionLEDs = coopertitionLEDs;
       addRequirements(coopertitionLEDs);
    
    }
    @Override
    public void execute() {
        FieldElement fieldElement = colorDetector.getCurrentElement();
        if(FieldElement.CUBE.equals(fieldElement)) {
            coopertitionLEDs.prettyColors(36, 36, 182);
        } else if(FieldElement.CONE.equals(fieldElement)) {
            coopertitionLEDs.prettyColors(125, 117, 12);
        } else {
            coopertitionLEDs.prettyColors(0, 0, 0);
        }
        super.execute();
    }
    
}
