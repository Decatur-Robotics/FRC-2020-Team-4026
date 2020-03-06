/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalIndexerSubsystem;
import frc.robot.subsystems.VerticalIndexerSubsystem;

public class StopIndexers extends CommandBase {
  /**
   * Creates a new StopIndexers.
   */
  HorizontalIndexerSubsystem horizontalIndexer;
  VerticalIndexerSubsystem verticalIndexer;

  public StopIndexers(HorizontalIndexerSubsystem horizontalIndexer, VerticalIndexerSubsystem verticalIndexer) {
    this.horizontalIndexer = horizontalIndexer;
    this.verticalIndexer = verticalIndexer;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(horizontalIndexer, verticalIndexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    horizontalIndexer.stop();
    verticalIndexer.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    horizontalIndexer.stop();
    verticalIndexer.stop();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    horizontalIndexer.stop();
    verticalIndexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}