package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BalanceSubsystem extends SubsystemBase {
    
    public GenericEntry[] yay;
    // public GenericEntry happiness;
    public GenericEntry irange;
    public GenericEntry out;
    public GenericEntry atSetpoint;

    public BalanceSubsystem() {

        yay = new GenericEntry[] {

            Shuffleboard.getTab("mine").add("p", 0.01).getEntry(),
            Shuffleboard.getTab("mine").add("i", 0).getEntry(),
            Shuffleboard.getTab("mine").add("d", 0).getEntry(),
            Shuffleboard.getTab("mine").add("t", 0).getEntry(),
            Shuffleboard.getTab("mine").add("ff", 0).getEntry()
        };
        // happiness = Shuffleboard.getTab("mine").add("setpointerror", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
        irange = Shuffleboard.getTab("mine").add("irange", 0).getEntry();
        out = Shuffleboard.getTab("mine").add("out", 0).getEntry();
        atSetpoint = Shuffleboard.getTab("mine").add("atsetpoint", false).getEntry();
    }
}