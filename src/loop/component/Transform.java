package loop.component;

import com.artemis.Component;
import loop.*;

public class Transform extends Component {
  public Position position;

  public Transform() {
    position = new Position();
  }
}
