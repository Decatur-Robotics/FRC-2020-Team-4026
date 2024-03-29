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
import frc.robot.PidParameters;
import frc.robot.TeamSparkMAX;
import frc.robot.TeamUtils;

public class HoodedShooterSubsystem extends SubsystemBase {
  private double maxRotationSpeed = 5600.;
  // Use 24500 for bot at init line
  // Values for 95% (max power for shooting)
  private final TeamSparkMAX shooter_main;
  private final TeamSparkMAX shooter_follow;
  /** Creates a new ShooterSubsystem. */
  private double shooterPower = 0.95;

  private static double kP = 0.0001,
      kI = 0.000000,
      kD = 0.00000,
      kF = 0.0005,
      kIZone = 0,
      kPeakOutput = 1,
      maxVel = 20000,
      maxAcc = 1500;
  private static int errorTolerance = 10;
  private PidParameters pidParameters;

  private final double spinUpTolerance = 200;

  public HoodedShooterSubsystem() {
    throw new IllegalArgumentException(
        "not allowed! ctor must provide parameters for all dependencies");
  }

  public HoodedShooterSubsystem(
      TeamSparkMAX shooter_follow, TeamSparkMAX shooter_main, PidParameters pidParameters) {
    this.shooter_follow = Objects.requireNonNull(shooter_follow, "shooter_follow must not be null");
    this.shooter_main = Objects.requireNonNull(shooter_main, "shooter_main must not be null");
    this.pidParameters = Objects.requireNonNull(pidParameters, "pidParameters must not be null");

    shooter_main.setInverted(false);
    shooter_follow.setInverted(true);
    stop();
  }

  public static HoodedShooterSubsystem Create() {
    TeamSparkMAX shooter_follow =
        new TeamSparkMAX("Subsystems.Shooter.Bottom", Ports.FollowerShooterMotorCAN);
    TeamSparkMAX shooter_lead =
        new TeamSparkMAX("Subsystems.Shooter.Top", Ports.LeadShooterMotorCAN);
    PidParameters pidParameters =
        new PidParameters(kP, kI, kD, kF, kIZone, kPeakOutput, maxVel, maxAcc, errorTolerance);
    return new HoodedShooterSubsystem(shooter_follow, shooter_lead, pidParameters);
  }

  @Override
  public void periodic() {
    shooter_follow.periodic();
    shooter_main.periodic();
    pidParameters.periodic("Subsystems.Shooter.Main", shooter_main, 0);
    pidParameters.periodic("Subsystems.Shooter.Follow", shooter_follow, 0);

    // This method will be called once per scheduler run
    shooterPower =
        -Math.abs(SmartDashboard.getNumber("Subsystems.Shooter.shooterPower", shooterPower));
    SmartDashboard.putNumber("Subsystems.Shooter.shooterPower", shooterPower);

    SmartDashboard.putNumber("Subsystems.Shooter.maxRotationSpeed", maxRotationSpeed);

    SmartDashboard.putBoolean("Subsystems.Shooter.isShooterReady", this.isShooterReady());

    SmartDashboard.putNumber("Subsystems.Shooter.yAngleAdjusted", this.getVisionYAngle());
    SmartDashboard.putNumber("Subsystems.Shooter.knotDistance", this.getKnotDistance());

    SmartDashboard.putNumber(
        "Subsystems.HoodedShooter.MainSpeedPer100ms", this.getShooterMainSpeed());
    SmartDashboard.putNumber(
        "Subsystems.HoodedShooter.FollowerSpeedPer100ms", this.getShooterFollowSpeed());

    kP = SmartDashboard.getNumber("Subsystems.Shooter.kP", kP);
    pidParameters.kP = kP;
    SmartDashboard.putNumber("Subsystems.Shooter.kP", kP);
    kI = SmartDashboard.getNumber("Subsystems.Shooter.kI", kI);
    pidParameters.kI = kI;
    SmartDashboard.putNumber("Subsystems.Shooter.kI", kI);
    kD = SmartDashboard.getNumber("Subsystems.Shooter.kD", kD);
    pidParameters.kD = kD;
    SmartDashboard.putNumber("Subsystems.Shooter.kD", kD);
    kF = SmartDashboard.getNumber("Subsystems.Shooter.kF", kF);
    pidParameters.kF = kF;
    SmartDashboard.putNumber("Subsystems.Shooter.kF", kF);
    kIZone = SmartDashboard.getNumber("Subsystems.Shooter.kIZone", kIZone);
    pidParameters.kIZone = kIZone;
    SmartDashboard.putNumber("Subsystems.Shooter.kIZone", kIZone);
    kPeakOutput = SmartDashboard.getNumber("Subsystems.Shooter.kPeakOutput", kPeakOutput);
    pidParameters.kPeakOutput = kPeakOutput;
    SmartDashboard.putNumber("Subsystems.Shooter.kPeakOutput", kPeakOutput);
    /*SmartDashboard.putNumber("Subsystems.Shooter.kPeakOutputBot", kPeakOutputBot);
    maxVelTop = SmartDashboard.getNumber("Subsystems.Shooter.maxVelTop", maxVelTop);
    topPidParameters.maxVel = maxVelTop;
    SmartDashboard.putNumber("Subsystems.Shooter.maxVelTop", maxVelTop);
    maxVelBot = SmartDashboard.getNumber("Subsystems.Shooter.maxVelBot", maxVelBot);
    botPidParameters.maxVel = maxVelBot;
    SmartDashboard.putNumber("Subsystems.Shooter.maxVelBot", maxVelBot);
    kIZoneTop = SmartDashboard.getNumber("Subsystems.Shooter.maxAccTop", maxAccTop);
    topPidParameters.maxAcc = maxAccTop;
    SmartDashboard.putNumber("Subsystems.Shooter.maxAccTop", maxAccTop);
    maxAccBot = SmartDashboard.getNumber("Subsystems.Shooter.maxAccBot", maxAccBot);
    botPidParameters.maxAcc = maxAccBot;
    SmartDashboard.putNumber("Subsystems.Shooter.maxAccBot", maxAccBot);*/
    errorTolerance =
        (int) SmartDashboard.getNumber("Subsystems.Shooter.errorTolerance", errorTolerance);
    pidParameters.errorTolerance = errorTolerance;
    SmartDashboard.putNumber("Subsystems.Shooter.errorTolerance", errorTolerance);
  }

  public boolean isShooterReady() {
    return ((Math.abs(shooter_main.getVelocityError()) <= spinUpTolerance)
        && (Math.abs(shooter_follow.getVelocityError()) <= spinUpTolerance));
  }

  public void setMotor(double speed) {
    this.shooter_main.setSmartMotionVelocity(speed);
    this.shooter_follow.setSmartMotionVelocity(speed);
  }

  public double getShooterPower() {
    return shooterPower;
  }

  public void stop() {
    this.shooter_main.setSmartMotionVelocity(0);
    this.shooter_main.set(0);
    this.shooter_follow.setSmartMotionVelocity(0);
    this.shooter_follow.set(0);
  }

  public double getShooterSpeed() {
    return getShooterMainSpeed();
  }

  public double getShooterMainSpeed() {
    return shooter_main.canEncoder.getVelocity();
  }

  public double getShooterFollowSpeed() {
    return shooter_follow.canEncoder.getVelocity();
  }

  public void setShooterVel(double speed) {
    shooter_main.configureWithPidParameters(pidParameters, 0);
    shooter_main.setSmartMotionVelocity(speed);
    shooter_follow.configureWithPidParameters(pidParameters, 0);
    shooter_follow.setSmartMotionVelocity(speed);
  }

  public double getMaxVel() {
    return maxRotationSpeed;
  }

  public void setShooterVelFraction(double speedFraction) {
    setShooterVel((speedFraction * maxRotationSpeed));
  }

  public void setShooterPower(double power) {
    shooter_main.set(power);
    shooter_follow.set(power);
  }

  public double getVisionYAngle() {
    Object result = TeamUtils.getFromNetworkTable("angles", "yAngle");
    if (result == null) {
      return 4026.0;
    } else {
      return (Double) result + 37.0;
    }
  }

  public double getKnotDistance() {
    return (1 / Math.tan(Math.toRadians(this.getVisionYAngle()))) * 54.5;
  }
}
