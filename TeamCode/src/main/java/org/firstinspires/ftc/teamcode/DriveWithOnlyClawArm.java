package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriveWithOnlyClawArm", group="OpMode")
public class DriveWithOnlyClawArm extends OpMode{

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Motors
    DcMotor leftBack; //port 3
    DcMotor leftFront; //port 0
    DcMotor rightBack; //port 2
    DcMotor rightFront; //port 1

    DcMotor rnpUp;

    //Variables
    double speedMultiplier;
    boolean aPressed_1;
    final int minPos = -10000;
    final int maxPos = 10000;

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

        //Set run mode
        rnpUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Initialize the variables
        speedMultiplier = 1;
        aPressed_1 = false;

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

        //Moving the claw arm

        //Move the arm up and down
        int position = rnpUp.getCurrentPosition();
        double pwr = gamepad2.left_stick_y;
        int newPos = (int) (pwr * 10) + position;
        if (minPos < newPos && newPos < maxPos) {
            rnpUp.setTargetPosition(newPos);
        }

        //Display data
        telemetry.addData("Runtime: ", getRuntime());
        telemetry.addData("x: ", x);
        telemetry.addData("y: ", y);
        telemetry.addData("speed multiplier: ", speedMultiplier);
        telemetry.addData("position", position);
        telemetry.addData("new pos", newPos);
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
        Left joystick to move the claw arm up and down

     */

}