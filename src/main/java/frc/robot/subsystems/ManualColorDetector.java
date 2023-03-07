package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ManualColorDetector extends ColorDetector {

    private GenericEntry coneOrCube;
    private FieldElement currentElement;

    public ManualColorDetector() {

        currentElement = FieldElement.CUBE;
        coneOrCube = Shuffleboard.getTab("RobotData").add("Cone or Cube", false).withProperties(Map.of("Color when true", "#800080", "Color when false", "#FFF000")).withPosition(2, 0).getEntry();
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

            coneOrCube.setBoolean(FieldElement.CUBE.equals(currentElement));
            //System.out.println(currentElement.toString() + "-------------------------------------------------------------------------------------");
        }
    }

}