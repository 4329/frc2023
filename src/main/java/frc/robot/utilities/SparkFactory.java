package frc.robot.utilities;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class SparkFactory {
    
  /**
   * The default method to be used when creating a new CANSparkMax, which gives it
   * basic settings for ease of use.
   * 
   * @param id
   * @return a new CAN object
   */
  public static CANSparkMax createCANSparkMax(int id) {

    CANSparkMax canToMake = new CANSparkMax(id, MotorType.kBrushless);
    canToMake.restoreFactoryDefaults();
    canToMake.clearFaults();
    canToMake.enableSoftLimit(SoftLimitDirection.kForward, false);
    canToMake.enableSoftLimit(SoftLimitDirection.kReverse, false);
    canToMake.setSmartCurrentLimit(Constants.ModuleConstants.kDriveCurrentLimit);
    canToMake.enableVoltageCompensation(Constants.DriveConstants.kVoltCompensation);
    canToMake.setIdleMode(IdleMode.kBrake);
    canToMake.follow(null);
    canToMake.burnFlash();
    return canToMake;
  }
}
