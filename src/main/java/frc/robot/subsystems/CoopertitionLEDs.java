package frc.robot.subsystems;

import java.util.Set;

import org.w3c.dom.css.RGBColor;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ColorDetector.FieldElement;

public class CoopertitionLEDs extends SubsystemBase {
   
    public final double saturation;
    public final double brightness;
    public AddressableLED addressableLED;
    public AddressableLEDBuffer addressableLEDBuffer;
    public ColorDetector colorDetector;
    public CoopertitionLEDs(ColorDetector colorDetector) {
       brightness = 1;
       saturation = 1;
       addressableLED = new AddressableLED(9);
       addressableLEDBuffer = new AddressableLEDBuffer(60);
       addressableLED.setLength(addressableLEDBuffer.getLength());
       addressableLED.setData(addressableLEDBuffer);
       addressableLED.start();
       this.colorDetector = colorDetector;
    }
   
    public void colorDetector() {

        if(colorDetector.detectElement() == FieldElement.CUBE) {
           prettyColors(36, 36, 182);
        } else if(colorDetector.detectElement() != FieldElement.CUBE) {
            prettyColors(0, 0, 0);
        } else if(colorDetector.detectElement() == FieldElement.CONE) {
            prettyColors(125, 117, 12);
        }
    }
    public void prettyColors(int r, int g, int b) {

        
    for(int i = 0; i < addressableLEDBuffer.getLength(); i++) {

        addressableLEDBuffer.setRGB(i, r, g, b);
    }
    
    addressableLED.setData(addressableLEDBuffer);
        }
    }