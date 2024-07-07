package Auto;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

//
public class Roboter {

	final private Brick brick = BrickFinder.getDefault();
	final private Port s1 = brick.getPort("S1");
//	final private Port s2 = brick.getPort("S2");
//	final private Port s3 = brick.getPort("S3");
//	final private Port s4 = brick.getPort("S4");

//	private EV3ColorSensor colorSensor = new EV3ColorSensor(s2);
//	private SampleProvider colorSP = colorSensor.getRedMode();

	private EV3TouchSensor touchSensor = new EV3TouchSensor(s1);
	private SampleProvider touchSP = touchSensor.getTouchMode();

	/* samples[0] -> colorSensor | samples[1] -> touchSensosr */
	private float[] samples = new float[2];

	private MotorStrg motorStrg = new MotorStrg();

//	public void followLine(int speed) {
//		colorSensor.setFloodlight(true);
//		colorSensor.setFloodlight(Color.RED);
//		motorStrg.setSpeed(speed);
//		motorStrg.setDirectionForward();
//		while (samples[1] != 1) {
//			colorSP.fetchSample(samples, 0);
//			if (samples[0] > 0.55) {
//				motorStrg.spinLeft(5);
//				colorSP.fetchSample(samples, 0);
//				if (samples[0] > 0.55) {
//					motorStrg.spinRight(10);
//				}
//			}
//			touchSP.fetchSample(samples, 1);
//		}
//		motorStrg.closeMotors();
//		colorSensor.close();
//		touchSensor.close();
//	}
	
	public void test(int speed) throws InterruptedException{
		motorStrg.setSpeed(speed);
		motorStrg.setDirectionForward();
		motorStrg.turnLeft(5);
		Thread.sleep(1000);
		motorStrg.turnRight(10);
//		motorStrg.driveDistance(20);
		motorStrg.stop();
		motorStrg.closeMotors();
		
	}
}