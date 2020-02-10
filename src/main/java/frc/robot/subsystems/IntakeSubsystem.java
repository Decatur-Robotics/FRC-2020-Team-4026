/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new IntakeSubsystem.
   */
  private final WPI_TalonSRX intake;

  private final double intakeSpeed = .5;
  private final double outtakeSpeed = -.5;
  
  public IntakeSubsystem() {
    intake = new WPI_TalonSRX(Constants.IntakeCAN);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void inTake(){
      intake.set(intakeSpeed);
  }
  public void outTake(){
      intake.set(outtakeSpeed);
  }
  public void stop(){
  intake.set(0);
  }

}
