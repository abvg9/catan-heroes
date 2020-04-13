package io.github.notaphplover.catan.core.agent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.ICatanGame;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.MinimunRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Agent tests")
public class AgentTest {

  @Nested
  @DisplayName("Agent.handle")
  class Handle {

    @DisplayName("It should send pending request to process")
    @Test
    public void itShouldSendPendingRequestsToProcess() {

      ICatanGame game = Mockito.mock(ICatanGame.class);
      doNothing().when(game).processRequest(any(IRequest.class));

      Agent agent = new MininumAgent(game);

      IPlayer player = new Player(0, new ResourceManager());

      IRequest missingRequest = new MinimunRequest(player, RequestType.BUILD_CONNECTION);

      player.registerMiss(missingRequest);

      agent.handle(new Command(player, CommandType.DIE));

      verify(game).processRequest(missingRequest);
    }
  }
}
