package io.github.notaphplover.catan.core.agent;

import io.github.notaphplover.catan.core.command.ICommand;

public interface IAgent {

  void handle(ICommand command);
}
