package robot;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class DriveStop {
	public static void main(String[] args)
    {
        System.out.println("Drive Forward\nand Stop\n");
        System.out.println("Press any key to start");

        Button.LEDPattern(4);     // flash green led and
        Sound.beepSequenceUp();   // make sound when ready.

        Button.waitForAnyPress();
        System.out.println("Running motors...");

        // create two motor objects to control the motors.
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);

        // set motors to 50% power.
        motorA.setPower(50);
        motorB.setPower(50);

        // wait 2 seconds.
        System.out.println("Waiting 2 seconds");

        Delay.msDelay(2000);

        // stop motors with brakes on. 
        System.out.println("Stopping motors...");

        motorA.stop();
        motorB.stop();

        // free up motor resources. 
        motorA.close(); 
        motorB.close();
 
        Sound.beepSequence(); // we are done.
    }
}
