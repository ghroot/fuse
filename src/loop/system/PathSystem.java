package loop.system;

import javax.vecmath.*;
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

	public PathSystem() {
		super(Aspect.all(Transform.class, Path.class));
	}

  @Override
	protected void process(int entity) {
    Transform transform = transformMapper.get(entity);
    Path path = pathMapper.get(entity);

    float speed = 0.3f;
    Vector2f dir = new Vector2f(path.targetPosition.x - transform.position.x, path.targetPosition.y - transform.position.y);
    if (dir.length() > 0) {
      float distance = dir.length();
      dir.normalize();
      if (speed > distance) {
        dir.scale(distance);
      }
      else {
        dir.scale(speed);
      }
      transform.position.add(dir);
    }
	}
}
