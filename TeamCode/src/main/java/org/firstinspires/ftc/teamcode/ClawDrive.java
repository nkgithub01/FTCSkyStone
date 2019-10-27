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
    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;

    DcMotor rnpUp;
    DcMotor rnpOut;

    //Servos
    Servo clawTurn;
    Servo claw;

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
        rnpOut = hardwareMap.get(DcMotor.class, "rnpOut");

        //Set zero power behavior
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rnpUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rnpOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set directions of the motors
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        rnpUp.setDirection(DcMotor.Direction.FORWARD);
        rnpOut.setDirection(DcMotor.Direction.FORWARD);

        //Initialize Servos
        clawTurn = hardwareMap.get(Servo.class, "clawTurn");
        claw = hardwareMap.get(Servo.class, "claw");

        //Set directions of the servos
        clawTurn.setDirection(Servo.Direction.FORWARD);
        claw.setDirection(Servo.Direction.FORWARD);

        //Initial position
        clawTurn.setPosition(0.2);
        claw.setPosition(0.2);

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
        if(!gamepad2.a) {
            aPressed_2 = false;
        }

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

}