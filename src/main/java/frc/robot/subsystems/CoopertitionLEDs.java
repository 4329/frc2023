package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoopertitionLEDs extends SubsystemBase {

    public final double saturation;
    public final double brightness;
    public AddressableLED addressableLED;
    public AddressableLEDBuffer addressableLEDBuffer;

    public CoopertitionLEDs() {
        brightness = 1;
        saturation = 1;
        addressableLED = new AddressableLED(9);
        addressableLEDBuffer = new AddressableLEDBuffer(60);
        addressableLED.setLength(addressableLEDBuffer.getLength());
        addressableLED.setData(addressableLEDBuffer);
        addressableLED.start();

    }

    public void prettyColors(int r, int g, int b) {

        for (int i = 0; i < addressableLEDBuffer.getLength(); i++) {

            addressableLEDBuffer.setRGB(i, r, g, b);
        }

        addressableLED.setData(addressableLEDBuffer);
    }
}