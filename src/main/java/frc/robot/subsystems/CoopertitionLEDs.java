package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoopertitionLEDs extends SubsystemBase {

    private final double saturation;
    private final double brightness;
    private AddressableLED addressableLED;
    private AddressableLEDBuffer addressableLEDBuffer;
    private int rainbowFirstPixelHue;

    public CoopertitionLEDs() {
        brightness = 1;
        saturation = 1;
        addressableLED = new AddressableLED(5);
        addressableLEDBuffer = new AddressableLEDBuffer(37);
        addressableLED.setLength(addressableLEDBuffer.getLength());
        addressableLED.setData(addressableLEDBuffer);
        addressableLED.start();

    }

    public void prettyColors(int r, int g, int b) {

        for (int i = 0; i < addressableLEDBuffer.getLength(); i++) {

            addressableLEDBuffer.setRGB(i, r, b, g);
        }

        addressableLED.setData(addressableLEDBuffer);
    }

    public void rainbow() {
        // For every pixel
        for (var i = 0; i < addressableLEDBuffer.getLength(); i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (rainbowFirstPixelHue + (i * 180 / addressableLEDBuffer.getLength())) % 180;
          // Set the value
          addressableLEDBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowFirstPixelHue += 1;
        // Check bounds
        rainbowFirstPixelHue %= 180;
        addressableLED.setData(addressableLEDBuffer);

      }
}