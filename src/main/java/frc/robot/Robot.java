/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogGyro;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Declaration of all the objects for our robot
  public Joystick leftJoystick;
  public Joystick rightJoystick;
  public CANTalon leftMotor;
  public CANTalon rightMotor;
  public DifferentialDrive driveTrain;

  // A single solenoid that can be set to on or off positions
  private final Solenoid solenoid;

  // A double solenoid can be used to control extension and retraction at the same time
  // Can be set to forward, reverse, or off
  private final DoubleSolenoid doubleSolenoid;

  private static final int kSolenoidButton = 1;
  private static final int kDoubleSolenoidForward = 2;
  private static final int kDoubleSolenoidReverse = 3;
  // gyro calibration constant, may need to be adjusted;
	// gyro value of 360 is set to correspond to one full revolution
  private static final double voltsPerDegreePerSecond = 0.0128;
  private final double tolerance = 0.1;
  public int autoCase;
  
  private CANSparkMax shooterSpark;

  public Timer autoTimer;
  public AnalogGyro gyro;

  // This method needs to be called before any variables are used
  // It should be called in RobotInit
  // It inializes all the objects declared in this file
  public init() {
      // Joystick objects take a port as a paratmeter
      // Port is what USB port they are plugged into your computer
      leftJoystick = new Joystick(0);
      rightJoystick = new Joystick(1);

      // CANTalon objects take The device ID as a parameter
      // The device ID must be flashed onto each Talon and is unique per device
      leftMotor = new CANTalon(0);
      rightMotor = new CANTalon(1);

      // DifferentialDrive is a class that gives us access to methods that can be used to drive our Robot
      // It takes speedController objects as parameters and uses them to drive the robot based on user input
      driveTrain = new DifferentialDrive(leftMotor, rightMotor);

      // Solenoid objects take the channel number for where it is plugged into the PCB
      solenoid = new Solenoid(0);

      // DoubleSolenoids take two channel numbers for where it is plugged into the PCB
      doubleSolenoid = new DoubleSolenoid(1, 2);

      // CANSpark takes the CAN id (assigned when firmware is flashed) and the motor type (Brushless or Brushed)
      shooterSpark = new CANSparkMax(2, MotorType.kBrushles);

      autoTimer = new Timer();
      // AnalogGyro takes the port number it is plugged into
      gyro = new AnalogGyro(0);
      gyro.setSensitivity(kVoltsPerDegreePerSecond);
      // The reset method sets the current angle the gyro is at to be 0
      gyro.reset();

      autoCase = 0;
  }

  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    init();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    //Start the timer
    autoTimer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code here
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     // Put default auto code here
    //     break;
    // }

    // if (autoTimer.get() < 3) {
    //   driveForward(0.3);
    // }
    
    // if(autoTimer.get() >=3 && autoTimer.get() < 6) {
    //   turnUsingGyro(gyro.get(), 45.0);
    // }
    
    // if(autoTimer.get() >=6 && autoTimer.get() < 7) {
    //   shoot();
    // }

    // if(autoTimer.get() >=7 {
    //   stop();
    // }

    // Each case does one thing and ends with a condition
    // When the condition is met, move to the next case
    switch (autoCase) {
      case 0:
        driveForward(0.3);

        if autoTimer.get() >= 3) {
          autoCase ++;
        }
        break;

      case 1:
        boolean doneTurning = turnUsingGyro(gyro.get(), 45.0, 0.3, 0.3);

        if (doneTurning) {
          autoCase ++;
          autoTimer.reset();
        }
        break;

      case 2:
        shoot();

        if(autoTimer.get() >= 1) {
          stop();
          autoCase ++;
        }
        break;
    }
  }

  /**
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    driveTrain.tankDrive(leftJoystick.getY(), rightJoystick.getY());
    checkForJoystickInput();
  }

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  // Method to take input from controllers and do stuff with it
  // Does everything but drive
  public void checkForJoystickInput() {
    
    //getRawButton returns true if the button is currently beinng pressed
    if (leftJoystick.getRawButton(1)) {
      // Sets solenoid
      doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    if (leftJoystick.getRawButton(2)) {
      doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    // If button 2 is pressed set solenoid to true
    // If it's not being pressed, set to false
    if (leftJoystick.getRawButton(2)) {
      solenoid.set(true);
    }
    else {
      solenoid.set(false);
    }

    // An example of some more readable code using methods
    if (leftJoystick.getTriggerPressed()) {
      shoot();
    }
    if (leftJoystick.getRawButton(3)) {
      stopShooter();
    }

    
  }

  // Putting all the methods here in one giant file will work, but it will get cluttered
  // We can create out own shooter class and move these methods into there for readability
  public void shoot() {
    // Shoot at full speed
    shooterSpark.set(1.0);
  }

  public void stopShooter() {
    // Set speed to 0
    shooterSpark.set(0.0);
  }

  public void driveForward(double speed) {
    leftMotor.set(speed);
    rightMotor.set(speed);
  }

  public void driveBackward(double speed) {
    leftMotor.set(-1 * speed);
    rightMotor.set(-1 * speed);
  }

  public void stop() {
    leftMotor.set(0);
    rightMotor.set(0);
    stopShooter();
  }

  public boolean turnUsingGyro(double currentAngle, double destinationAngle, double reverseSpeed, double forwardSpeed) {
    //Positive angle is roatation clockwise
    //currentAngle is the angle measured off the gyro
    //destinationAngle is the desired angle to rotate to
    //forwardSpeed and reverseSpeed are variables between 1.0 and -1.0, the larger they are the faster you will turn.
    //    making one the negative of the other will 
    if ( abs(currentAngle) >= abs(destinationAngle) - tolerance || abs(currentAngle) <= abs(destinationAngle) + tolerance) {
      //we're there, stop turning
      drivetrain.tankdrive(0,0)
      return true;
    } else if ( destinationAngle  > currentAngle) {
      //rotate clockwise
      drivetrain.tankDrive(reverseSpeed, forwardSpeed);
      return false;
    } else if ( destinationAngle < currentAngle) {
      //rotate counter-clockwise
      drivetrain.tankDrive(forwardSpeed, reverseSpeed);
      return false;
    }
  }

}
