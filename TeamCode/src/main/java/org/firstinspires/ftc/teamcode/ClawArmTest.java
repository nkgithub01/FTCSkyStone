package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="ClawArmTest", group="OpMode")
public class ClawArmTest extends OpMode{

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Motors
    DcMotor rnpUp;

    //Variables
    final int minPos = -2500;
    final int maxPos = 5000;

    @Override
    public void init() {

        //Initialize DcMotors
        rnpUp = hardwareMap.get(DcMotor.class, "rnpUp");

        //Set zero power behavior
        rnpUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set directions of the motors
        rnpUp.setDirection(DcMotor.Direction.FORWARD);

        //Set run mode
        rnpUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Tell user that initialization is complete
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        //Moving the claw arm

        //Move the arm up and down
        int position = rnpUp.getCurrentPosition();
        double pwr = -gamepad2.left_stick_y;
        int newPos = (int) (pwr * 200) + position;
        if (minPos < newPos && newPos < maxPos) {
            rnpUp.setTargetPosition(newPos);
            rnpUp.setPower(pwr);
        }

        //Display data
        telemetry.addData("Runtime: ", getRuntime());
        telemetry.addData("position", position);
        telemetry.addData("new pos", newPos);
    }

}