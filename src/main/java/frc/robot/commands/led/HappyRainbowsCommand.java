package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CoopertitionLEDs;

public class HappyRainbowsCommand extends CommandBase {

    private CoopertitionLEDs coopertitionLEDs;

    public HappyRainbowsCommand(CoopertitionLEDs coopertitionLEDs) {

        addRequirements(coopertitionLEDs);
        this.coopertitionLEDs = coopertitionLEDs;
    }

    @Override
    public void execute() {

        coopertitionLEDs.rainbow();
        System.out.println("rainbow+++++++++++++++++++");

    }

}