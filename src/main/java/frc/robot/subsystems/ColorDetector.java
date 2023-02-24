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
    private final Color cube;
    private final Color cubeLogo;
    private final Color cone;

    public Color matchColor;
    public Color rawColor;

    public enum FieldElement {
        
        CUBE, CONE, NOTHIN
    }

    public ColorDetector() {

        i2cPort = I2C.Port.kMXP;
        colorSensorV3 = new ColorSensorV3(i2cPort);
        colorMatch = new ColorMatch();
        cube = new Color(36 / 255.0, 36 / 255.0, 182 / 255.0);
        cubeLogo = new Color(60 / 255.0, 115/ 255.0, 79 / 255.0);
        cone = new Color(85 / 255.0, 136 / 255.0, 24 / 255.0);

        colorMatch.addColorMatch(cube);
        //colorMatch.addColorMatch(cubeLogo);
        colorMatch.addColorMatch(cone);
        coneOrCube = Shuffleboard.getTab("setpoints").add("Cone or Cube?", "NOTHIN").getEntry();
        proximity = Shuffleboard.getTab("setpoints").add("Proximity", 1).getEntry();
    }

    public FieldElement detectElement() {

        rawColor = colorSensorV3.getColor();
        matchColor = colorMatch.matchClosestColor(rawColor).color;
        if (cone.equals(matchColor)) {

            return FieldElement.CONE;
        } else if (cube.equals(matchColor) || cubeLogo.equals(matchColor)) {

            return FieldElement.CUBE;
        } else {

            return FieldElement.NOTHIN;
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
        } else {

            coneOrCube.setString(FieldElement.NOTHIN.toString());
        }
        proximity.setDouble(distance());
    }

}
