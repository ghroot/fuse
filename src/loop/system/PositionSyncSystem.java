package loop.system;

import java.util.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import fuse.*;
import loop.*;
import loop.component.*;

public class PositionSyncSystem extends IteratingSystem {
  ComponentMapper<Transform> transformMapper;
  ComponentMapper<Fuse> fuseMapper;

	public PositionSyncSystem() {
		super(Aspect.all(Transform.class, Fuse.class));
	}

	protected void process(int entity) {
    Transform transform = transformMapper.get(entity);
    Fuse fuse = fuseMapper.get(entity);

    if (fuse.lastSyncedPosition == null || !fuse.lastSyncedPosition.equals(transform.position)) {
      try {
        // TODO: Find way to push data to fuse without changing access levels
        fuse.user.room.send(fuse.user, "move|" + fuse.user.name + "|" + transform.position.x + "," + transform.position.y + ",0;0,0,0,0", true);
      } catch (Exception e) {
        e.printStackTrace();
      }
      fuse.lastSyncedPosition = new Position(transform.position.x, transform.position.y);
    }
	}
}
