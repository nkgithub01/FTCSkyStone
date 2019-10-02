package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriveSimple", group="OpMode")
public class DriveSimple extends OpMode{

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Motors
    DcMotor leftBack = null;
    DcMotor leftFront = null;
    DcMotor rightBack = null;
    DcMotor rightFront = null;


    @Override
    public void init() {

        //Initialize DcMotors
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        //Set zero power behavior
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set directions of the motors
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

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

        //Driving
        leftFront.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y);
        rightFront.setPower(gamepad1.left_stick_x - gamepad1.left_stick_y);
        leftBack.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y);
        rightBack.setPower(-gamepad1.left_stick_x - gamepad1.left_stick_y);

        //Turning
        if (Math.abs(gamepad1.right_stick_x) >= 0.000001) {
            if (gamepad1.right_stick_x > 0) {
                setAllDriveMotorPower(1);
            } else if (gamepad1.right_stick_x < 0) {
                setAllDriveMotorPower(-1);
            }
        }

        //Display runtime
        telemetry.addData("Runtime: ", getRuntime());
    }

    public void setAllDriveMotorPower(double power) {
        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);
    }

}