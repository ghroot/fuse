package loop.component;

import com.artemis.Component;
import loop.*;

public class Path extends Component {
  public Position targetPosition;

  public Path() {
    targetPosition = new Position();
  }
}
