/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.turretCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class PointTurretAtTargetWithAngleCommand extends CommandBase {
  TurretSubsystem turret;
  VisionSubsystem vision;
  /** Creates a new PointTurretAtTargetWithAngleCommand. */
  public PointTurretAtTargetWithAngleCommand(TurretSubsystem turret) {
    this.turret = turret;
    this.vision = turret.getVisionSubsystem();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (vision.isValid()) {
      double angleSetPoint = turret.getRadians() - Math.toRadians(vision.getLastSeenTx());
      turret.startRotatingToPosition(angleSetPoint);
    } else {
      turret.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
