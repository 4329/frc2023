package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ManualColorDetector extends ColorDetector {

    GenericEntry coneOrCube;

    private FieldElement currentElement;


    

    public ManualColorDetector() {

        currentElement = FieldElement.CONE;
        coneOrCube = Shuffleboard.getTab("RobotData").add("Cone or Cube?", "" + currentElement).getEntry();
    }

    public void toggleElement() {

        if (FieldElement.CONE.equals(currentElement)) {

            currentElement = FieldElement.CUBE;
        } else {
            
            currentElement = FieldElement.CONE;
        }
       
    }

    @Override
    public FieldElement detectElement() {

        return currentElement;
    }

    @Override
    public void periodic() {

        if (currentElement != null) {
            coneOrCube.setString(currentElement.toString());
        }
    }

}