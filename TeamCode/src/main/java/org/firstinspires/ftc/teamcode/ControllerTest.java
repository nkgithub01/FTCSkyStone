package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ControlTest", group = "OpMode")
public class ControllerTest extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop(){
        telemetry.addData("X: ", gamepad1.left_stick_x);
        telemetry.addData("y: ", gamepad1.left_stick_y);
    }
}
