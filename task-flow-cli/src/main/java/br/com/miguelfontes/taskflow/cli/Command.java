package br.com.miguelfontes.taskflow.cli;

/**
 * A marker interface to compute generic commands
 *
 * @author Miguel Fontes
 */
public interface Command {
    /**
     * Executes this command operation
     */
    void execute();
}
