package frc.robot.commands.autos;

import java.util.HashMap;
import java.util.Map;

import org.littletonrobotics.junction.Logger;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.*;
import frc.robot.Constants.SwerveDrivetrainConstants;

public final class AutonMaster {
    
    public static final Map<String, Command> eventMap = new HashMap<>(Map.ofEntries(
        Map.entry("Stop", new InstantCommand(RobotContainer.m_swerveDrivetrain::stopSwerve)),
        Map.entry("ZeroGyro", new InstantCommand(() -> RobotContainer.m_swerveDrivetrain.m_pigeonGyro.setYaw(RobotContainer.m_swerveDrivetrain.getYaw().getDegrees() + 180)))
    ));

    private static final SwerveAutoBuilder m_autoBuilder = new SwerveAutoBuilder(
        RobotContainer.m_swerveDrivetrain::getPose, 
        RobotContainer.m_swerveDrivetrain::resetOdometry,
        AutonConstants.AUTO_TRANSLATION_PID,
        AutonConstants.AUTO_ROTATION_PID, 
        RobotContainer.m_swerveDrivetrain::setChassisSpeeds, 
        eventMap, 
        true, 
        RobotContainer.m_swerveDrivetrain);

    public static CommandBase testAuto() {
        return m_autoBuilder.fullAuto(PathPlanner.loadPathGroup("Test", new PathConstraints(4, 3)));
    }

    public AutonMaster() {
        throw new UnsupportedOperationException("Master Auton Class!");
    }

}
