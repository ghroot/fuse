package loop.component;

import javax.vecmath.*;
import com.artemis.*;
import fuse.*;
import loop.*;

public class Fuse extends Component {
  public Router.User user;
  public Point2f lastSyncedPosition;

  public Fuse() {
    lastSyncedPosition = new Point2f();
  }
}
