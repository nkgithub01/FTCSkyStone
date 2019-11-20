package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ClawTest", group = "OpMode")
public class ClawTest extends OpMode {

    //Servos
    //Servo claw;
    Servo clawLeft;
    Servo clawRight;

    //Variables
    /*boolean aPressed;
    boolean closed;*/



    @Override
    public void init() {

        //Initialize Servo
        /*claw = hardwareMap.get(Servo.class, "claw");

        //Set servo direction
        claw.setDirection(Servo.Direction.FORWARD);

        //Initial position
        claw.setPosition(0.2);

        //Initialize variables
        aPressed = false;
        closed = false;*/

        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");


        clawRight.setDirection(Servo.Direction.FORWARD);
        clawLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop(){

        //Control claw
        /*if(!gamepad1.a) aPressed = false;
        if(gamepad1.a && !aPressed)
        {
            aPressed = true;
            if(closed)
            {
                claw.setPosition(0.7);
                closed = false;
            }
            else
            {
                claw.setPosition(0.2);
                closed = true;
            }
        }

        //Add data
        telemetry.addData("X: ", gamepad1.left_stick_x);
        telemetry.addData("y: ", gamepad1.left_stick_y);*/

        if(gamepad1.right_stick_y > 0)
        {
            clawRight.setPosition(1);
            clawLeft.setPosition(1);
        }
        else if(gamepad1.right_stick_y < 0) {
            clawRight.setPosition(0);
            clawLeft.setPosition(0);
        }
        else {
            clawRight.setPosition(0.5);
            clawLeft.setPosition(0.5);
        }

    }
}
