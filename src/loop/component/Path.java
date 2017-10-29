package loop.component;

import javax.vecmath.*;
import com.artemis.*;
import loop.*;

public class Path extends Component {
  public Point2f targetPosition;

  public Path() {
    targetPosition = new Point2f();
  }
}
