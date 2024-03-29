/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Objects;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Ports;
import frc.robot.TeamTalonSRX;

public class HorizontalIndexerSubsystem extends SubsystemBase {
  private final TeamTalonSRX horizontalIndexer;

  private final double intakeSpeed = -.4;
  private final double outtakeSpeed = .5;

  public HorizontalIndexerSubsystem() {
    throw new IllegalArgumentException(
        "not allowed! ctor must provide parameters for all dependencies");
  }

  public HorizontalIndexerSubsystem(TeamTalonSRX horizontalIndexer) {
    this.horizontalIndexer =
        Objects.requireNonNull(horizontalIndexer, "horizontalIndexer must not be null");
  }

  public static HorizontalIndexerSubsystem Create() {
    TeamTalonSRX horizontalIndexer =
        new TeamTalonSRX("Subsystems.HorizontalIndexer.HIndxMotor", Ports.IndexerHorizCAN);
    return new HorizontalIndexerSubsystem(horizontalIndexer);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    horizontalIndexer.periodic();
    SmartDashboard.putNumber(
        "Subsystems.HorizontalIndexer.Position", horizontalIndexer.getSelectedSensorPosition());
  }

  public void intake() {
    horizontalIndexer.set(intakeSpeed);
  }

  public void outtake() {
    horizontalIndexer.set(outtakeSpeed);
  }

  public void stop() {
    horizontalIndexer.set(0);
  }
}
