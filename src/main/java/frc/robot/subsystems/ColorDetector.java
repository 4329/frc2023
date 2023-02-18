package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColorDetector extends SubsystemBase {

    private final I2C.Port i2cPort;
    private final ColorSensorV3 colorSensorV3;
    GenericEntry coneOrCube;
    GenericEntry proximity;
    ColorMatch colorMatch;
    Color cube;
    Color cone;
    Color matchColor;

    public ColorDetector() {
        i2cPort = I2C.Port.kMXP;
        colorSensorV3 = new ColorSensorV3(i2cPort);
        colorMatch = new ColorMatch();
        cube = new Color(36 / 255.0, 36 / 255.0, 182 / 255.0);
        cone = new Color(125 / 255.0, 117 / 255.0, 12 / 255.0);
        colorMatch.setConfidenceThreshold(Constants.ColorConstants.confidenceLevel);

        colorMatch.addColorMatch(cube);
        colorMatch.addColorMatch(cone);
        coneOrCube = Shuffleboard.getTab("setpoints").add("Cone or Cube?", "NOTHIN").getEntry();
        proximity = Shuffleboard.getTab("setpoints").add("Proximity", 1).getEntry();
    }

    public enum FieldElement {
        CUBE, CONE
    }

    public FieldElement detectElement() {

        colorMatch.matchClosestColor(colorSensorV3.getColor());
        matchColor = colorMatch.matchClosestColor(colorSensorV3.getColor()).color;
        if (matchColor == cone) {

            return FieldElement.CONE;
        } else if (matchColor == cube) {

            return FieldElement.CUBE;
        } else {
            return null;
        }
    }

    public double distance() {
        
        return colorSensorV3.getProximity();
    }

    @Override
    public void periodic() {
        FieldElement kraigElement = detectElement();
        if (kraigElement != null) {
            coneOrCube.setString(kraigElement.toString());
        }
        proximity.setDouble(distance());
    }
}