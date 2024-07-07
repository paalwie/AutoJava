package Auto;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class MotorStrg {

	private BaseRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	private BaseRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	public MotorStrg() {
		leftMotor.synchronizeWith(new RegulatedMotor[] { rightMotor });
	}

	public void setSpeed(int speed) {
		/*
		 * Setze Geschwindigkeit vom Motor auf "speed" (in Grad/Sekunde)
		 */
		leftMotor.startSynchronization();
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.endSynchronization();
	}

	public void driveDistance(int distance) {
		/*
		 * Roboter faehrt die gegebene Distanz in cm und bleibt dann stehen.
		 * positive distance -> forward | negative distance -> backward
		 */
		if (distance > 0) {
			this.setDirectionForward();
		} else {
			this.stop();
			this.setDirectionBackward();
		}
		int rotation = Math.round(distance * 360 / 17.6f);
		this.rotateWheels(rotation, rotation);
		this.stop();
	}

	public void setDirectionForward() {
		/*
		 * setzt die Fahrtrichtigung auf "vorwaerts"
		 */
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
	}

	public void setDirectionBackward() {
		/*
		 * setzt die Fahrtrichtung auf "rueckwaerts"
		 */
		leftMotor.startSynchronization();
		leftMotor.backward();
		rightMotor.backward();
		leftMotor.endSynchronization();
	}

	private void turn(int degrees, int radius, BaseRegulatedMotor m1,
			BaseRegulatedMotor m2) {
		/*
		 * 17.6 ist circa die Distanz, die der Roboter bei einer 360-Grad
		 * Drehung der Reifen faehrt. 11.5 der Abstand der Raeder.
		 */
		float deg = degrees / 180;
		float arc1 = (float) ((Math.PI * radius * deg) / 17.6 * 360);
		float arc2 = (float) ((Math.PI * (radius + 11.5) * deg) / 17.6 * 360);
		m1.rotate(Math.round(arc1), true);
		m2.rotate(Math.round(arc2), false);
//		this.rotateWheels(Math.round(arc1), Math.round(arc2));
		m1.waitComplete();
		m2.waitComplete();
		this.setDirectionForward();
	}

	public void rotateWheels(int rotation, int rotation2) {
		leftMotor.startSynchronization();
		leftMotor.rotate(rotation, true);
		rightMotor.rotate(rotation2, false);
		leftMotor.endSynchronization();
	}

	public void spinLeft(int y) {
		/*
		 * Roboter dreht sich um "y" Grad nach rechts.
		 */
		leftMotor.startSynchronization();
		this.turn(y, 0, rightMotor, leftMotor);
		leftMotor.endSynchronization();

	}

	public void spinRight(int y) {
		/*
		 * Roboter dreht sich um "y" Grad nach links
		 */
		leftMotor.startSynchronization();
		this.turn(y, 0, leftMotor, rightMotor);
		leftMotor.endSynchronization();
	}

	public void turnLeft(int radius) {
		/*
		 * Roboter faehrt eine Kurve nach links, mit einem x-cm Radius
		 */
		leftMotor.startSynchronization();
		this.turn(90, radius, rightMotor, leftMotor);
		leftMotor.endSynchronization();
	}

	public void turnRight(int radius) {
		/*
		 * Roboter faehrt eine Kurve nach rechts, mit einem x-cm Radius
		 */
		leftMotor.startSynchronization();
		this.turn(90, radius, leftMotor, rightMotor);
		leftMotor.endSynchronization();
	}

	public void stop() {
		/*
		 * Roboter haelt sofort an
		 */
		leftMotor.startSynchronization();
		rightMotor.stop(true);
		leftMotor.stop(true);
		leftMotor.endSynchronization();
	}

	public void setAcceleration(int acceleration) {
		/*
		 * Setzt die Beschleunigung auf "acceleration"
		 */
		leftMotor.startSynchronization();
		rightMotor.setAcceleration(acceleration);
		leftMotor.setAcceleration(acceleration);
		leftMotor.endSynchronization();
	}

	public boolean isStanding() {
		/*
		 * prueft ob Auto steht oder nicht. true = stehend, false = fahrend
		 * isMoving()" gibt auch dann <false> zurueck, wenn der Motor auf
		 * "vorwaerts/rueckwaerts" gestellt ist, die reifen sich aber nicht
		 * drehen.
		 */
		if (rightMotor.isMoving() || leftMotor.isMoving()) {
			return false;
		} else {
			return true;
		}
	}

	public void closeMotors() {
		/*
		 * Stellt beide Motoren ab. (Sollte nur einmal am Programmende benutzt
		 * werden)
		 */
		leftMotor.close();
		rightMotor.close();
	}

}
