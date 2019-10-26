package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Test", group = "OpMode")
public class Test extends OpMode {

    boolean aPressed = false;
    boolean closed = false;

    @Override
    public void init() {
    }

    @Override
    public void loop(){
        Servo claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);

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

        telemetry.addData("X: ", gamepad1.left_stick_x);
        telemetry.addData("y: ", gamepad1.left_stick_y);
    }
}
