package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="OneArmTest", group="OpMode")
public class OneArmTest extends OpMode {

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Motors
    DcMotor rnpUp1;
    DcMotor rnpUp2;

    //Variables
    final int minPos = 100;
    final int maxPos = 4600;

    int initial;
    @Override
    public void init() {

        //Initialize DcMotors
        rnpUp1 = hardwareMap.get(DcMotor.class, "rnpUp1");
        //rnpUp2 = hardwareMap.get(DcMotor.class, "rnpUp2");

        //Set zero power behavior
        rnpUp1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //rnpUp2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set directions of the motors
        rnpUp1.setDirection(DcMotor.Direction.FORWARD);
        //rnpUp2.setDirection(DcMotor.Direction.REVERSE);

        //Set run mode
        rnpUp1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //rnpUp2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Tell user that initialization is complete
        telemetry.addData("Status", "Initialized");
        initial = rnpUp1.getCurrentPosition();
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        //Moving the claw arm

        //Move the arm up and down
        int position1 = rnpUp1.getCurrentPosition();
        //int position2 = rnpUp2.getCurrentPosition();
        double pwr = -gamepad2.left_stick_y;
        int newPos1 = (int) (pwr * 200) + position1;
        if (minPos < newPos1 && newPos1 < maxPos) {
            rnpUp1.setTargetPosition(newPos1);
            rnpUp1.setPower(pwr);
        }

        /*int newPos2 = (int) (pwr*200) + position2;
        if (minPos < newPos2 && newPos2 < maxPos) {
            rnpUp2.setTargetPosition(newPos2);
            rnpUp2.setPower(pwr);
        }*/

        //Display data
        telemetry.addData("Runtime: ", getRuntime());
        telemetry.addData("position", rnpUp1.getCurrentPosition());
        telemetry.addData("new pos", newPos1);
        telemetry.addData("position change: ", position1 - initial);

        //telemetry.addData("position 2: ", position2);
        //telemetry.addData("new pos 2: ", newPos2);
    }

}