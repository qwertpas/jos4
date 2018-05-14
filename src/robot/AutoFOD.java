package robot;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Stopwatch;



public class AutoFOD {
	//takes around 8 seconds to load program
	
	public static void main(String[] args){
		
		Motors2 moto = new Motors2(MotorPort.A, MotorPort.B, MotorPort.C, MotorPort.D);
		EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S4);
		SampleProvider gyroSamples = gyro.getAngleMode();
		float[] angle = { 0.0f };
		float heading = 0;
		//takes around 7 seconds to initialize
		
		System.out.println("AutoFOD");
        System.out.println("Press any key to start");

        Button.LEDPattern(4);     // flash green led and
        Sound.beepSequenceUp();   // make sound when ready.

        Button.waitForAnyPress(); //BUTTON PRESS
        Stopwatch time = new Stopwatch();
        gyroSamples.fetchSample(angle, 0);
        float origAngle = angle[0];
        
        System.out.println("Running motors...");
        
        while(time.elapsed() < 60000) { //runs at most one minute
            gyroSamples.fetchSample(angle, 0);
        	heading = angle[0] - origAngle;
        	System.out.println(heading);
        	moto.moveGlobalAngle(0, heading, 0.5, 0);
            moto.executeEV3();
        }
        
        moto.stop();
        gyro.close();
        
        Sound.beepSequence(); //done
    }
}