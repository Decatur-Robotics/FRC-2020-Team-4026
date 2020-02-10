/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VerticalIndexerSubsystem;

public class VerticalIndexerUpCommand extends CommandBase {
  
  VerticalIndexerSubsystem verticalIndexer;
  /**
   * Creates a new VerticalIndexerUpCommand.
   */
  public VerticalIndexerUpCommand(VerticalIndexerSubsystem verticalIndexer) {
    this.verticalIndexer = verticalIndexer;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(verticalIndexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    verticalIndexer.up();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    verticalIndexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
