package frc.robot.intake.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeConfig;
import frc.robot.intake.IntakeRoller;

import java.util.function.Supplier;

public class RunIntake extends CommandBase {
    public enum Mode {
        kCube(IntakeConfig.kCubeProfile), kCone(IntakeConfig.kConeProfile), kOuttake(IntakeConfig.kOuttake), kHandoff(IntakeConfig.kHandoff);

        Mode(IntakeConfig.IntakeProfile profile) {
            kProfile = profile;
        }

        private final IntakeConfig.IntakeProfile kProfile;
    }

    private final IntakeRoller roller;
    private final Supplier<Mode> modeSupplier;
    private Mode lastMode;

    public RunIntake(IntakeRoller roller, Supplier<Mode> modeSupplier) {
        this.roller = roller;
        this.modeSupplier = modeSupplier;

        addRequirements(roller);
    }

    public RunIntake(IntakeRoller roller, Mode mode) {
        this.roller = roller;
        this.modeSupplier = () -> mode;

        addRequirements(roller);
    }

    @Override
    public String getName() {
        return "RunIntake";
    }

    @Override
    public void initialize() {
        lastMode = modeSupplier.get();
        roller.setProfile(lastMode.kProfile);
        roller.setOutput(1);
    }

    @Override
    public void execute() {
        final var currentMode= modeSupplier.get();
        if (currentMode != lastMode) {
            System.out.println("switching mode " + currentMode.name());
            lastMode = currentMode;
            roller.setProfile(lastMode.kProfile);
        }
    }

    @Override
    public void end(boolean interrupted) {
        roller.stop();
    }
}
