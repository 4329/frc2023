package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColorDetector extends SubsystemBase {

    private final I2C.Port i2cPort;
    private final ColorSensorV3 colorSensorV3;
    
    GenericEntry coneOrCube;
    GenericEntry proximityEntry;


    ColorMatch colorMatch;
    private final Color cube;
    private final Color cubeLogo;
    private final Color cone;
    private GenericEntry colorGraph;

    public Color matchColor;
    public Color color;

    public enum FieldElement {
        
        CUBE, CONE, NOTHIN
    }

    public ColorDetector() {

        i2cPort = I2C.Port.kMXP;
        colorSensorV3 = new ColorSensorV3(i2cPort);
        colorMatch = new ColorMatch();
        cube = new Color(0.2, 0.33, 0.469);
        cubeLogo = new Color(0.238, 0.479, 0.283);
        cone = new Color(0.35, 0.566, 0.08);

        colorMatch.addColorMatch(cube);
        colorMatch.addColorMatch(cubeLogo);
        colorMatch.addColorMatch(cone);
        coneOrCube = Shuffleboard.getTab("setpoints").add("Cone or Cube?", "NOTHIN").getEntry();
        proximityEntry = Shuffleboard.getTab("setpoints").add("Proximity", 1).getEntry();
        colorGraph = Shuffleboard.getTab("setpoints").add("colors", new double[]{1, 1, 1}).getEntry();
    }

    public FieldElement detectElement() {

        color = colorSensorV3.getColor();
    
        colorGraph.setDoubleArray(new double []{color.blue, color.red, color.green});
        matchColor = colorMatch.matchClosestColor(color).color;
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

        double proximity = distance();
        
        if (proximity > 125) {


            FieldElement kraigElement = detectElement();
            if (kraigElement != null) {
                
                coneOrCube.setString(kraigElement.toString());
            } else {

                coneOrCube.setString(FieldElement.NOTHIN.toString());
            }
        }
        proximityEntry.setDouble(proximity);
    }

}
