/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivingCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class SetSpeedMode extends CommandBase {
  DriveTrainSubsystem drive;
  /** Creates a new SetSpeedMode. */
  public SetSpeedMode(DriveTrainSubsystem drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = drive;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setDriveTrainMode(DriveTrainSubsystem.DriveTrainMode.FAST);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setDriveTrainMode(DriveTrainSubsystem.DriveTrainMode.SLOW);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
