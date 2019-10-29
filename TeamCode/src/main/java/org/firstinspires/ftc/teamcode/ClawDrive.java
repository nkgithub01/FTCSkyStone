package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="DriveSimple", group="OpMode")
public class ClawDrive extends OpMode{

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Motors
    DcMotor leftBack; //port 3
    DcMotor leftFront; //port 0
    DcMotor rightBack; //port 1
    DcMotor rightFront; //port 2

    DcMotor rnpUp;

    //Servos
    Servo clawTurn;
    Servo claw;
    Servo rnpOut;

    //Variables
    double speedMultiplier;
    boolean aPressed_1;
    boolean aPressed_2;
    boolean clawClosed;

    @Override
    public void init() {

        //Initialize DcMotors
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        rnpUp = hardwareMap.get(DcMotor.class, "rnpUp");

        //Set zero power behavior
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rnpUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set directions of the motors
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        rnpUp.setDirection(DcMotor.Direction.FORWARD);

        //Initialize Servos
        clawTurn = hardwareMap.get(Servo.class, "clawTurn");
        claw = hardwareMap.get(Servo.class, "claw");
        rnpOut = hardwareMap.get(Servo.class, "rnpOut");

        //Set directions of the servos
        clawTurn.setDirection(Servo.Direction.FORWARD);
        claw.setDirection(Servo.Direction.FORWARD);
        rnpOut.setDirection(Servo.Direction.FORWARD);

        //Initial position
        clawTurn.setPosition(0.2);
        claw.setPosition(0.2);
        rnpOut.setPosition(0.2);

        //Initialize the variables
        speedMultiplier = 1;
        aPressed_1 = false;
        aPressed_2 = false;
        clawClosed = false;

        //Tell user that initialization is complete
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        //Drive the robot

        //Normalize the values if the sum is greater than one to fit motor power
        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double sum = Math.abs(x) + Math.abs(y);

        if (sum > 1) {
            double newx = x / sum, newy = y / sum;
            x = newx;
            y = newy;
        }

        x *= speedMultiplier;
        y *= speedMultiplier;

        //Driving
        leftFront.setPower(-x + y);
        rightFront.setPower(-x - y);
        leftBack.setPower(x + y);
        rightBack.setPower(x - y);

        //Turning
        if (Math.abs(gamepad1.right_stick_x) >= 0.000001) {
            setAllDriveMotorPower(-gamepad1.right_stick_x);
        }

        //If A on gamepad1 is not pressed
        if (!gamepad1.a) {
            aPressed_1 = false;
        }

        //Toggle the speed multiplier
        if (gamepad1.a && !aPressed_1) {
            speedMultiplier = 1.2 - speedMultiplier;
            aPressed_1 = true;
        }

        //Control claw

        //If A on gamepad2 is not pressed
        if(!gamepad2.a) {
            aPressed_2 = false;
        }

        //Open and close the claw
        if(gamepad2.a && !aPressed_2)
        {
            aPressed_2 = true;
            if(clawClosed)
            {
                claw.setPosition(0.7);
                clawClosed = false;
            }
            else
            {
                claw.setPosition(0.2);
                clawClosed = true;
            }
        }

        //Rotate the claw
        double dist = 0;
        dist += gamepad2.left_trigger;
        dist -= gamepad2.right_trigger;
        double newDist = dist / 100 + clawTurn.getPosition();
        if (Math.abs(newDist) < 1) {
            clawTurn.setPosition(newDist);
        }

        //Moving the claw arm

        //Move the arm up and down
        //tba

        //Move the claw forwards and backwards
        double power = gamepad2.right_stick_x;
        double extension = power / 100 + rnpOut.getPosition();
        if (Math.abs(extension) < 1) {
            rnpOut.setPosition(extension);
        }

        //Display data
        telemetry.addData("Runtime: ", getRuntime());
        telemetry.addData("x: ", x);
        telemetry.addData("y: ", y);
        telemetry.addData("speed multiplier: ", speedMultiplier);
    }

    public void setAllDriveMotorPower(double power) {
        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);
    }

    /*

    Controlling the robot:

    Gamepad1(Driving):
        Left joystick to move the robot
        Right joystick to turn
        Press A to toggle speed multiplier

    Gamepad2(Claw/Claw Arm):
        Left joystick to move the claw arm up
        Right joystick to move the claw forwards and backwards
        Press A to open and close the claw
        Use triggers to rotate the claw

     */

}