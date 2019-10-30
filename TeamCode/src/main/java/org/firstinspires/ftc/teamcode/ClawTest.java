package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ClawTest", group = "OpMode")
public class ClawTest extends OpMode {

    //Servos
    Servo claw;

    //Variables
    boolean aPressed;
    boolean closed;


    @Override
    public void init() {

        //Initialize Servo
        claw = hardwareMap.get(Servo.class, "claw");

        //Set servo direction
        claw.setDirection(Servo.Direction.FORWARD);

        //Initial position
        claw.setPosition(0.2);

        //Initialize variables
        aPressed = false;
        closed = false;

    }

    @Override
    public void loop(){

        //Control claw
        if(!gamepad1.a) aPressed = false;
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
        telemetry.addData("y: ", gamepad1.left_stick_y);
    }
}
