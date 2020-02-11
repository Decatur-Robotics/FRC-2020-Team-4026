/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalIndexerSubsystem;

public class HorizontalIndexerOuttakeCommand extends CommandBase {
  HorizontalIndexerSubsystem horizontalIndexer;

  /**
   * Creates a new HorizontalIndexerOuttakeCommand.
   */
  public HorizontalIndexerOuttakeCommand(HorizontalIndexerSubsystem horizontalIndexer) {
    this.horizontalIndexer = horizontalIndexer;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(horizontalIndexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    horizontalIndexer.outtake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    horizontalIndexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
