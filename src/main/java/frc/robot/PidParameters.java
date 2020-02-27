package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PidParameters {
    public double kP, kI, kD, kF, kIZone, kPeakOutput;
    public int errorTolerance;

    public PidParameters(double kP, double kI, double kD, double kF, 
            double kIZone, double kPeakOutput,
            int errorTolerance){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.kIZone = kIZone;
        this.kPeakOutput = kPeakOutput;
        this.errorTolerance = errorTolerance;
    }


    @Override
    public PidParameters clone() {
        return new PidParameters(kP, kI, kD, kF, kIZone, kPeakOutput, errorTolerance);
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null )
            return false;
        if (!(obj instanceof PidParameters))
            return false;
        
        PidParameters otherPP = (PidParameters) obj;

        return otherPP.kP==kP &&
            otherPP.kI==kI &&
            otherPP.kD==kD &&
            otherPP.kF==kF &&
            otherPP.kIZone==kIZone &&
            otherPP.kPeakOutput==kPeakOutput &&
            otherPP.errorTolerance==errorTolerance;
    }

    /**
     * Use these parameters on a motor
     */
    public void configureMotorWithPidParameters(TeamTalonSRX motor, int pidSlotIndex) {
        motor.configureWithPidParameters(this, pidSlotIndex);
    }

    public void periodic(String prefix, TeamTalonSRX motor, int pidSlotIndex) {
        // We update the motor immediately when the the motor is in a PID-controlled mode
        boolean updateMotor = motor.isRunningPidControlMode();

        double new_kF = SmartDashboard.getNumber(prefix + ".PID.kF", kF);
        if ( new_kF != kF ) {
            kF = new_kF;
            if (updateMotor) motor.config_kF(pidSlotIndex, kF);
        }
        SmartDashboard.putNumber(prefix + ".PID.kF", kF);

        double new_kP = SmartDashboard.getNumber(prefix + ".PID.kP", kP);
        if ( new_kP != kP ) {
            kP = new_kP;
            if (updateMotor) motor.config_kP(pidSlotIndex, kP);
        }
        SmartDashboard.putNumber(prefix + ".PID.kP", kP);

        double new_kI = SmartDashboard.getNumber(prefix + ".PID.kI", kI);
        if ( new_kI != kI ) {
            kI = new_kI;
            if (updateMotor) motor.config_kI(pidSlotIndex, kI);
        }
        SmartDashboard.putNumber(prefix + ".PID.kI", kI);

        double new_kD = SmartDashboard.getNumber(prefix + ".PID.kD", kD);
        if ( new_kD != kD ) {
            kD = new_kD;
            if (updateMotor) motor.config_kD(pidSlotIndex, kD);
        }
        SmartDashboard.putNumber(prefix + ".PID.kD", kD);

        double new_kPeakOutput = SmartDashboard.getNumber("Subsystems.Turret.kPeakOutput", kPeakOutput);
        if ( new_kPeakOutput != kPeakOutput ) {
            kPeakOutput = new_kPeakOutput;
            if (updateMotor) motor.configPeakOutputForward(kPeakOutput);
            if (updateMotor) motor.configPeakOutputReverse(-kPeakOutput);
        }
        SmartDashboard.putNumber("Subsystems.Turret.kPeakOutput", kPeakOutput);

        int new_errorTolerance = (int) SmartDashboard.getNumber("Subsystems.Turret.errorTolerance", errorTolerance);
        if ( new_errorTolerance != errorTolerance ) {
            errorTolerance = new_errorTolerance;
            if (updateMotor) motor.configAllowableClosedloopError(pidSlotIndex, errorTolerance, 30);
        }
        SmartDashboard.putNumber("Subsystems.Turret.errorTolerance", errorTolerance);
    }
}