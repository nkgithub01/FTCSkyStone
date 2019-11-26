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

    DcMotor rnpUp1;
    DcMotor rnpUp2;

    //Variables
    int minPos;
    int maxPos;

    double speedMultiplier;

    boolean aPressed_1;
    boolean downPressed = false;
    boolean upPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;

    @Override
    public void init() {

        //Initialize DcMotors
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        rnpUp1 = hardwareMap.get(DcMotor.class, "rnpUp1");
        rnpUp2 = hardwareMap.get(DcMotor.class, "rnpUp2");

        //Set zero power behavior
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rnpUp1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rnpUp2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set directions of the motors
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        rnpUp1.setDirection(DcMotor.Direction.REVERSE);
        rnpUp2.setDirection(DcMotor.Direction.FORWARD);

        //Set run mode
        rnpUp1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rnpUp2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Initialize the variables
        speedMultiplier = 1;
        aPressed_1 = false;
        minPos = 0;
        maxPos = 3800;

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
        int position1 = rnpUp1.getCurrentPosition();
        int position2 = rnpUp2.getCurrentPosition();
        double pwr = -gamepad2.left_stick_y;
        int newPos1 = (int) (pwr * 200) + position1;
        if (minPos < newPos1 && newPos1 < maxPos) {
            rnpUp1.setTargetPosition(newPos1);
            rnpUp1.setPower(pwr);
        }

        int newPos2 = (int) (pwr*200) + position2;
        if (minPos < newPos2 && newPos2 < maxPos) {
            rnpUp2.setTargetPosition(newPos2);
            rnpUp2.setPower(pwr);
        }

        //Change limits
        if (gamepad2.dpad_down && !downPressed) {
            maxPos -= 100;
            downPressed = true;
        }
        if (!gamepad2.dpad_down) {
            downPressed = false;
        }

        if (gamepad2.dpad_up && !upPressed) {
            maxPos += 100;
            upPressed = true;
        }
        if (!gamepad2.dpad_up) {
            upPressed = false;
        }

        if (gamepad2.dpad_left && !leftPressed) {
            minPos -= 100;
            leftPressed = true;
        }
        if (!gamepad2.dpad_left) {
            leftPressed = false;
        }

        if (gamepad2.dpad_right && !rightPressed) {
            minPos += 100;
            rightPressed = true;
        }
        if (!gamepad2.dpad_right) {
            rightPressed = false;
        }

        //Display data
        telemetry.addData("Runtime: ", getRuntime());

        telemetry.addData("x: ", x);
        telemetry.addData("y: ", y);
        telemetry.addData("speed multiplier: ", speedMultiplier);

        telemetry.addData("position 1: ", position1);
        telemetry.addData("new pos 1: ", newPos1);

        telemetry.addData("position 2: ", position2);
        telemetry.addData("new pos 2: ", newPos2);
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
        Left gamepad to change min and max limits

     */

}