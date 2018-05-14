package robot;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;



public class RCFOD {
	//takes around 8 seconds to load program
	
	public static void main(String[] args){
		
		Motors2 moto = new Motors2(MotorPort.A, MotorPort.B, MotorPort.C, MotorPort.D);
		EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S4);
		SampleProvider gyroSamples = gyro.getAngleMode();
		EV3IRSensor ir = new EV3IRSensor(SensorPort.S1);

		float[] angle = { 0.0f };
		float heading = 0;
		int x = 0;
		int y = 0;
		int spin = 0;
		boolean changed = false;
		boolean done = false;
		

		//takes around 12 seconds to initialize
		
		System.out.println("AutoFOD");
        System.out.println("Press any key to start");

        Button.LEDPattern(4);     // flash green led and
        Sound.beepSequenceUp();   // make sound when ready.

        Button.waitForAnyPress(); //BUTTON PRESS
        
        Delay.msDelay(500);
        
        gyroSamples.fetchSample(angle, 0);
        float origAngle = angle[0];
        
        System.out.println("Running motors...");
        
        while(!done) {
            gyroSamples.fetchSample(angle, 0);
        	heading = angle[0] - origAngle;
//        	System.out.println(direction);
            moto.executeEV3();
            
        	changed = true;
            int control = ir.getRemoteCommand(0);
            
            switch (control) {
            case 1:		//front left
            	x = -1;
            	y = 1;
            	break;
            case 2:		//back left
            	x = -1;
                y = -1;
                break;
            case 3:		//front right
            	x = 1;
                y = 1;
                break;
            case 4:		//back right
            	x = 1;
                y = -1;
                break;
            case 5:		//both front buttons
            	x = 0;
                y = 1;
                break;
            case 6:		//front left and back right
            	x = 0;
                y = 0;
                spin = -1;
                break;
            case 7:		//front right and back left
            	x = 0;
                y = 0;
                spin = 1;
                break;
            case 8:		//both back buttons
            	x = 0;
                y = -1;
                break;
            case 9:		//on-off switch at the top
            	x = 0;
                y = 0;
                spin = 0;
                gyro.reset();
                Sound.beep();  // make sound when reset.
                Delay.msDelay(3000);
                if (ir.getRemoteCommand(0) == 9) {
                	done = true;
                }
                break;
            case 10:	//both left buttons
            	x = -1;
                y = 0;
                break;
            case 11:	//both right buttons
            	x = 1;
                y = 0;
                break;
            default:	//nothing pressed
            	spin = 0;
            	moto.clear();
            	changed = false;
                break;
            }
            
            if(changed) {
            	moto.moveGlobalVector(x, y, heading, spin);
            	System.out.println(heading);
            }
        	

        }
        
        moto.stop();
        gyro.close();
        ir.close();
        
        Sound.beepSequence(); //done
    }
}