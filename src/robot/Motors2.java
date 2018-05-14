package robot;


import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.Port;

public class Motors2 {
	
	private UnregulatedMotor aDrive;
	private UnregulatedMotor bDrive;
	private UnregulatedMotor cDrive;
	private UnregulatedMotor dDrive;
	
	private double aPower = 0;
    private double bPower = 0;
    private double cPower = 0;
    private double dPower = 0;
	
	
	//constructor
	public Motors2(Port portA, Port portB, Port portC, Port portD) {
		aDrive = new UnregulatedMotor(portA);
		bDrive = new UnregulatedMotor(portB);
		cDrive = new UnregulatedMotor(portC);
		dDrive = new UnregulatedMotor(portD);
	}
	
	public void executeEV3(){
        aDrive.setPower(intClip(aPower*100, -100, 100));
        bDrive.setPower(intClip(bPower*100, -100, 100));
        cDrive.setPower(intClip(cPower*100, -100, 100));
        dDrive.setPower(intClip(dPower*100, -100, 100));
    }
	
	//move the robot in an angle relative to the field
    public void moveGlobalAngle(double angle, double heading, double power, double spin) {
        double radians = Math.toRadians(angle - 45);
        double[] cartesianCoords = Calculate.polarToCartesian(power, radians, true);
        double[] globalVector = Calculate.FOD(cartesianCoords[0], cartesianCoords[1], heading, false, true); //heading is positive for ev3 but for REV expansion its negative
        double x = globalVector[0];
        double y = globalVector[1];
        aPower = -x + spin;
        bPower = y + spin;
        cPower = x + spin;
        dPower = -y + spin;
    }
    
    
  //move the robot in an angle relative to the field
    public void moveGlobalVector(double xComp, double yComp, double heading, double spin) {
        double[] globalVector = Calculate.FOD(xComp, yComp, heading + 45, true, true); //heading is positive for ev3 but for REV expansion its negative
        double x = globalVector[0];
        double y = globalVector[1];
        aPower = -x + spin;
        bPower = y + spin;
        cPower = x + spin;
        dPower = -y + spin;
    }
    
    public void moveLocalAngle(double angle, double power, double spin) {
        double degrees = Math.toRadians(angle - 45);
        double x = Math.cos(degrees) * power;
        double y = Math.sin(degrees) * power;
        aPower = -x + spin;
        bPower = y + spin;
        cPower = x + spin;
        dPower = -y + spin;
    }
    
  //zeros them out (stops drivetrain)
    public void clear() {
        aPower = 0;
        bPower = 0;
        cPower = 0;
        dPower = 0;
        executeEV3();
    }

    //stops and closes motors entirely
    public void stop() {
    	aPower = 0;
        bPower = 0;
        cPower = 0;
        dPower = 0;
        executeEV3();
        aDrive.close();
        bDrive.close();
        cDrive.close();
        dDrive.close();
    }


    public void setPowers(double aPower, double bPower, double cPower, double dPower) {
        this.aPower = aPower;
        this.bPower = bPower;
        this.cPower = cPower;
        this.dPower = dPower;
    }
    
  //THE GETTERS ##################################################

    public double[] getPowers(){ return new double[] {aPower, bPower, cPower, dPower}; }
    public double getAPower(){ return aPower; }
    public double getBPower(){ return bPower; }
    public double getCPower(){ return cPower; }
    public double getDPower(){ return dPower; }

	
	
	public int intClip(double value, int min, int max) {
		if(value > max) { return max; }
		else if(value < min) { return min; }
		else { return (int)Math.round(value); }
	}
}
