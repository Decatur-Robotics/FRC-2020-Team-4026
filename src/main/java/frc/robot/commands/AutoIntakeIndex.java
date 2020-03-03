/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalIndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.VerticalIndexerSubsystem;

public class AutoIntakeIndex extends CommandBase {
  /**
   * Creates a new AutoIntakeIndex.
   */
  private final HorizontalIndexerSubsystem horizontalIndexer;
  private final VerticalIndexerSubsystem verticalIndexer;
  private final IntakeSubsystem intake;

  private boolean ballAtBot;
  private boolean ballAtTop;
  private boolean ballLeftBot;

  private String currentState;

  private final SimpleIntakeCommand intakeCommand;
  private final HorizontalIndexerIntakeCommand horizontalIntakeCommand;
  private final TransferBall transferBallCommand;

  public AutoIntakeIndex(IntakeSubsystem intake, HorizontalIndexerSubsystem horizontalIndexer, VerticalIndexerSubsystem verticalIndexer) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.horizontalIndexer = horizontalIndexer;
    this.verticalIndexer = verticalIndexer;
    this.intake = intake;

    intakeCommand = new SimpleIntakeCommand(intake);
    horizontalIntakeCommand = new HorizontalIndexerIntakeCommand(horizontalIndexer);
    transferBallCommand = new TransferBall(verticalIndexer, horizontalIndexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeCommand.schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ballLeftBot = ballAtBot && !verticalIndexer.middleSwitchIsPressed();
    ballAtBot = verticalIndexer.bottomSwitchIsPressed();
    ballAtTop = verticalIndexer.topSwitchIsPressed();

    if (ballAtBot && !ballAtTop && !transferBallCommand.isScheduled()){
      currentState = "transfer";
      horizontalIntakeCommand.cancel();
      transferBallCommand.schedule();
    }
    else if (!ballAtBot && !transferBallCommand.isScheduled()){
      currentState = "pulling";
      horizontalIntakeCommand.schedule();
    }
    else if (ballAtTop && ballAtBot && horizontalIntakeCommand.isScheduled()){
      currentState = "full";
      horizontalIntakeCommand.cancel();
    } 
    else if (!ballAtBot && !transferBallCommand.isScheduled() && !horizontalIntakeCommand.isScheduled()){
      currentState = "top full";
      horizontalIntakeCommand.schedule();
    }
    SmartDashboard.putString("Commands.AutoIntakeIndex.CurrentState", currentState);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    currentState = "end";
    SmartDashboard.putString("Commands.AutoIntakeIndex.CurrentState", currentState);
    if (transferBallCommand.isScheduled()){
      transferBallCommand.cancel();
    }
    if (intakeCommand.isScheduled()){
      intakeCommand.cancel();
    }
    if (horizontalIntakeCommand.isScheduled()){
      horizontalIntakeCommand.cancel();
    }
    currentState = "";
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
