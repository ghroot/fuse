package loop.system;

import java.util.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import fuse.*;
import loop.*;
import loop.component.*;

public class PathSystem extends IteratingSystem {
  ComponentMapper<Transform> transformMapper;
  ComponentMapper<Path> pathMapper;
  ComponentMapper<Fuse> fuseMapper;

  private List<QueuedRule> queuedRules;
  private Map<Router.User, Position> pendingTargetPositions;

	public PathSystem(List<QueuedRule> queuedRules) {
		super(Aspect.all(Transform.class, Path.class, Fuse.class));
    this.queuedRules = queuedRules;
    pendingTargetPositions = new HashMap<Router.User, Position>();
	}

  @Override
  protected void begin() {
    super.begin();

    for (int i = queuedRules.size() - 1; i >= 0; i--) {
      QueuedRule queuedRule = queuedRules.get(i);
      if (queuedRule.data[0].equals("path")) {
        queuedRules.remove(i);
        String[] pos = queuedRule.data[2].split(",");
        pendingTargetPositions.put(queuedRule.user, new Position(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])));
      }
    }
  }

	protected void process(int entity) {
    Transform transform = transformMapper.get(entity);
    Path path = pathMapper.get(entity);
    Fuse fuse = fuseMapper.get(entity);

    Position pendingTargetPosition = pendingTargetPositions.get(fuse.user);
    if (pendingTargetPosition != null) {
      pendingTargetPositions.remove(fuse.user);
      path.targetPosition = pendingTargetPosition;
    }

    float speed = 0.3f;
    if (transform.position.x < path.targetPosition.x) {
      transform.position.x += Math.min(path.targetPosition.x - transform.position.x, speed);
    }
    else if (transform.position.x > path.targetPosition.x) {
      transform.position.x -= Math.min(transform.position.x - path.targetPosition.x, speed);
    }
    if (transform.position.y < path.targetPosition.y) {
      transform.position.y += Math.min(path.targetPosition.y - transform.position.y, speed);
    }
    else if (transform.position.y > path.targetPosition.y) {
      transform.position.y -= Math.min(transform.position.y - path.targetPosition.y, speed);
    }
	}
}
