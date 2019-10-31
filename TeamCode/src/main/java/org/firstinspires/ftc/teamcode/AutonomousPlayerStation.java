package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousPlayerStation", group="OpMode")
public class AutonomousPlayerStation extends LinearOpMode {

    //Objects
    ElapsedTime runtime = new ElapsedTime();

    //Motors
    DcMotor leftBack; //port 3
    DcMotor leftFront; //port 0
    DcMotor rightBack; //port 2
    DcMotor rightFront; //port 1

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
    final int minPos = -10000;
    final int maxPos = 10000;

    //Initialize the variables
    public void runOpMode() {

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
        rnpUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

        waitForStart();

    }

}