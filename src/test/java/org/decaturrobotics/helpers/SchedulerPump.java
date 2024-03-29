package org.decaturrobotics.helpers;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * Integration test helper class that will run the command scheduler. Use of this class requires
 * that your test class be extended from {@link TestWithScheduler}.
 */
public final class SchedulerPump {
  private static int defaultHeartbeatInMs = 20;

  /** Static class. Do not initialize. */
  private SchedulerPump() {}

  /**
   * Change the default heartbeat for the scheduler pump.
   *
   * @param defaultHeartbeatInMs Heartbeat in milliseconds
   */
  public static void setDefaultHeartbeat(int defaultHeartbeatInMs) {
    SchedulerPump.defaultHeartbeatInMs = defaultHeartbeatInMs;
  }

  /**
   * Helper to figure out what heartbeat to use.
   *
   * @param optionalHeartbeatInMs Optional heartbeat in array form to simulate optional parameters
   * @return The heartbeat to use
   */
  private static int getHeartbeatToUse(int[] optionalHeartbeatInMs) {
    if (optionalHeartbeatInMs.length > 1) {
      throw new IllegalArgumentException("There can be only one optional heartbeat parameter.");
    }
    return optionalHeartbeatInMs.length > 0 ? optionalHeartbeatInMs[0] : defaultHeartbeatInMs;
  }

  /**
   * Run the command scheduler every heartbeatInMs for a durationInMs amount of time. Calls will be
   * serialized as the Scheduler is not threadsafe, so beware of deadlocks. As of this writing,
   * parallel testing is NOT the default mode for JUnit. So if you have not decorated your tests to
   * run in parallel, you are fine.
   *
   * @param durationInMs Duration to run in milliseconds
   * @param optionalHeartbeatInMs Optional pump time in milliseconds. If omitted, 20ms default
   *     unless changed.
   * @throws InterruptedException Thrown if sleeping interrupted
   */
  public static synchronized void runForDuration(long durationInMs, int... optionalHeartbeatInMs)
      throws InterruptedException {
    int heartbeatToUseInMs = getHeartbeatToUse(optionalHeartbeatInMs);
    long start = System.nanoTime();
    while (System.nanoTime() < (start + TimeUnit.MILLISECONDS.toNanos(durationInMs))) {
      CommandScheduler.getInstance().run();
      Thread.sleep(heartbeatToUseInMs);
    }
  }
}
